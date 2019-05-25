package com.optiva.topup.voms.common.utils;

import com.optiva.topup.voms.common.entities.voucherconfig.Promotion;
import com.optiva.topup.voms.common.entities.voucherconfig.RechargeValue;
import com.optiva.topup.voms.common.entities.voucherconfig.Taxation;
import com.optiva.topup.voms.common.types.AmountType;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RechargeValueCalculator {

  public double calculateFinalValue(RechargeValue rechargeValue) {

    Set<Promotion> promotions = rechargeValue.getPromotions();
    Set<Taxation> taxations = rechargeValue.getTaxations();
    Double valuePromotion = rechargeValue.getValuePromotion();
    Double valueTaxation = rechargeValue.getValueTaxation();
    Double value = rechargeValue.getValue();

    Double totalPromotionValue = 0D;
    Double totalTaxationValue = 0D;

    if (promotions.isEmpty()) {
      totalPromotionValue = valuePromotion;
    } else {
      List<Promotion> activePromotions = filterValidPromotions(promotions);
      double promotionFixedValue = calculatePromotionsFixedValue(activePromotions);
      double promotionPercentageValue = calculatePromotionsPercentageValue(value, activePromotions);
      totalPromotionValue = valuePromotion + promotionFixedValue + promotionPercentageValue;
    }

    if (taxations.isEmpty()) {
      totalTaxationValue = valueTaxation;
    } else {
      List<Taxation> activeTaxations = filterValidTaxations(taxations);
      double taxationFixedValue = calculateTaxationsFixedValue(activeTaxations);
      double taxationPercentageValue = calculateTaxationsPercentageValue(value, activeTaxations);
      totalTaxationValue = valueTaxation + taxationFixedValue + taxationPercentageValue;
    }

    return value + totalPromotionValue - totalTaxationValue;
  }

  private double calculateTaxationsPercentageValue(Double value, List<Taxation> activeTaxations) {
    double totalPercentageValue = activeTaxations.stream()
        .filter(taxation -> taxation.getAmountType() == AmountType.PERCENTAGE).map(Taxation::getAmount)
        .reduce(0D, (first, second) -> first + second);
    return value * (totalPercentageValue / 100);
  }

  private double calculateTaxationsFixedValue(List<Taxation> activeTaxations) {
    return activeTaxations.stream().filter(taxation -> taxation.getAmountType() == AmountType.FIXED)
        .map(Taxation::getAmount).reduce(0D, (first, second) -> first + second);
  }

  private double calculatePromotionsPercentageValue(Double value, List<Promotion> activePromotions) {
    double totalPercentageValue = activePromotions.stream()
        .filter(promotion -> promotion.getAmountType() == AmountType.PERCENTAGE).map(Promotion::getAmount)
        .reduce(0D, (first, second) -> first + second);
    return value * (totalPercentageValue / 100);
  }

  private double calculatePromotionsFixedValue(List<Promotion> activePromotions) {
    return activePromotions.stream().filter(promotion -> promotion.getAmountType() == AmountType.FIXED)
        .map(Promotion::getAmount).reduce(0D, (first, second) -> first + second);
  }

  private List<Taxation> filterValidTaxations(Set<Taxation> taxations) {
    LocalDate todayDate = LocalDate.now();
    return taxations.stream().filter(taxation -> taxation.getStartDate().isBefore(todayDate))
        .filter(taxation -> taxation.getEndDate().isAfter(todayDate)).collect(Collectors.toList());
  }

  private List<Promotion> filterValidPromotions(Set<Promotion> promotions) {
    LocalDate todayDate = LocalDate.now();
    return promotions.stream().filter(promotion -> promotion.getStartDate().isBefore(todayDate))
        .filter(promotion -> promotion.getEndDate().isAfter(todayDate)).collect(Collectors.toList());
  }

}
