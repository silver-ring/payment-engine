package com.optiva.topup.voms.soap.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@XmlRootElement
public class RechargeRequest {

  @XmlElement(required = true)
  private String msisdn;
  @XmlElement(required = true)
  private Long voucherId;
  @XmlElement(required = true)
  private String transactionId;
  @XmlElement(required = true)
  private Integer msisdnProviderId;
  @XmlElement(required = true)
  private String ccId;

}
