package com.founder.util;

import java.util.List;
import java.util.Map;

public interface TemplateMailer 
{

    /**
    * Send a mail with both a text and a HTML version.
    * @param email the email address where to send the email
    * @param context a {@link Map} of objects to expose to the template engine
    * @param templatePrefix the prefix of the templates to use
    */
    void mail(String email, Map<String, ?> context, String templatePrefix);
    
        /**
    * Send a mass-mailing with both a text anda HTML version.
    * @param emails a {@link List} of email addresses where to send emails
    * @param contexts a {@link List} of {@link Map}s of objects to expose to the template engine
    * @param templatePrefix the prefix of the templates to us
    */
    void massMail(List<String> emails, List<Map<String, ?>> contexts, String templatePrefix);
}
