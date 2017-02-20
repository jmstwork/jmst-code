package com.founder.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @spring.bean id="templateMailer"
    <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
        <property name="host" value="smtpserver" />
        <property name="port" value="25" />
        <property name="username" value="username" /> 
        <property name="password" value="password" />
        <property name="javaMailProperties">
            <props>
                <prop key="mail.smtp.auth">true</prop>
            </props>
        </property>
    </bean>
    
    <bean id="freeMarkerConfigurer" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
        <property name="templateLoaderPath" value="/WEB-INF/freemakertemplate/" />
        <property name="freemarkerSettings">
            <props>
                <prop key="template_update_delay">0</prop>
                <prop key="default_encoding">UTF-8</prop>
                <prop key="locale">zh_CN</prop>
            </props>
        </property>
    </bean>
    
    <bean id="mailMessage" class="org.springframework.mail.SimpleMailMessage" singleton="false">
        <property name="from" value="xu_dengfeng@founder.co.jp"/>
        <property name="subject" value="test title"/>
        <property name="cc">
            <list>
                <value>mbis-pmo@founder.co.jp</value>
            </list>
        </property>
        <property name="bcc">
            <list>
                <value>mbis-kankyo@founder.co.jp</value>
            </list>
        </property>
        <property name="forceTo" value="xu_dengfeng@founder.co.jp"/>
        <property name="spool" value="true"/>
        <property name="senderName" value="���o��"/>
    </bean>  
       
    <bean id="mailEngine" class="test.MailEngine">
        <property name="mailSender" ref="mailSender"/>
        <property name="freeMarkerConfigurer" ref="freeMarkerConfigurer" />
    </bean>
 */
public class FreeMarkerTemplateMailerImpl implements TemplateMailer 
{
//    private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarkerTemplateMailerImpl.class);
    
    private JavaMailSender mailSender = null;
    private Configuration configuration = null;

    public void mail(final String email, final Map<String, ?> context, final String templatePrefix) 
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator() 
        {
            public void prepare(MimeMessage mimeMessage) throws MessagingException, IOException 
            {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                mimeMessage.setFrom(new InternetAddress("info@kalixia.com"));
                mimeMessage.setSubject("Votre inscription au site kalixia.com");

                Multipart mp = new MimeMultipart("alternative");

                // Create a "text" Multipart message
                BodyPart textPart = new MimeBodyPart();
                Template textTemplate = configuration.getTemplate(templatePrefix + "-text.ftl");
                final StringWriter textWriter = new StringWriter();
                try 
                {
                    textTemplate.process(context, textWriter);
                } 
                catch (TemplateException e) 
                {
                    throw new MailPreparationException("Can't generate text subscription mail", e);
                }
                textPart.setDataHandler(new DataHandler(new DataSource() 
                {
					public InputStream getInputStream() throws IOException 
                    {
                        return new ByteArrayInputStream(textWriter.toString().getBytes());
                    }
                    
                    public OutputStream getOutputStream() throws IOException 
                    {
                        throw new IOException("Read-only data");
                    }
                    
                    public String getContentType() 
                    {
                        return "text/plain";
                    }
                    
                    public String getName() 
                    {
                        return "main";
                    }
                }));
                mp.addBodyPart(textPart);

                // Create a "HTML" Multipart message
                Multipart htmlContent = new MimeMultipart("related");
                BodyPart htmlPage = new MimeBodyPart();
                Template htmlTemplate = configuration.getTemplate(templatePrefix + "-html.ftl");
                final StringWriter htmlWriter = new StringWriter();
                try 
                {
                    htmlTemplate.process(context, htmlWriter);
                } 
                catch (TemplateException e) 
                {
                    throw new MailPreparationException("Can't generate HTML subscription mail", e);
                }
                htmlPage.setDataHandler(new DataHandler(new DataSource() 
                {
					public InputStream getInputStream() throws IOException 
                    {
                        return new ByteArrayInputStream(htmlWriter.toString().getBytes());
                    }
                    
                    public OutputStream getOutputStream() throws IOException 
                    {
                        throw new IOException("Read-only data");
                    }
                    
                    public String getContentType() 
                    {
                        return "text/html";
                    }
                    
                    public String getName() 
                    {
                        return "main";
                    }
                }));
                htmlContent.addBodyPart(htmlPage);
                BodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(htmlContent);
                mp.addBodyPart(htmlPart);

                mimeMessage.setContent(mp);
            }
        };
        mailSender.send(preparator);
    }

    public void massMail(List<String> emails, List<Map<String, ?>> contexts, String templatePrefix) 
    {
        int i = 0;
        for (Iterator<String> iterator = emails.iterator(); iterator.hasNext(); i++) 
        {
            String email = iterator.next();
            mail(email, contexts.get( i ), templatePrefix);
        }
    }

    /** @spring.property ref="mailSender" */
    public void setMailSender(JavaMailSender mailSender) 
    {
        this.mailSender = mailSender;
    }

    /** 
     * @throws Exception 
     * @spring.property ref="freemarkerConfig" 
     */
    public void setFreeMarkerConfigurer(FreeMarkerConfigurationFactory confFactory) throws Exception 
    {
        this.configuration = confFactory.createConfiguration();
    }
}
