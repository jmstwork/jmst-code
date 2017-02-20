package com.founder.msg.utils;


public class Message
{
  private MessageHead head;
  private MessageBody body;

  public MessageHead getHead()
  {
    return this.head;
  }

  public void setHead(MessageHead head)
  {
    this.head = head;
  }

  public MessageBody getBody()
  {
    return this.body;
  }

  public void setBody(MessageBody body)
  {
    this.body = body;
  }
}