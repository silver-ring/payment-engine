package com.optiva.topup.voms.soap.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@XmlRootElement
public class RechargeResponse {

  private double amount;

  private String currency;

  private int providerIdVoucher;

  private int balanceExtension;

  private long serialNo;

  private int voucherType;

  private int errorCode;

  private int voucherExpiryDate;

  private int accountId;

}
