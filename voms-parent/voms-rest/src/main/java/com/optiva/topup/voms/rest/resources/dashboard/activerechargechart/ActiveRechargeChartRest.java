package com.optiva.topup.voms.rest.resources.dashboard.activerechargechart;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class ActiveRechargeChartRest {

  @GetMapping("/activeRechargeChart")
  public ResponseEntity<List<ActiveRechargeChartDto>> getActiveRechargeChartData() {
    final List<ActiveRechargeChartDto> activeRechargeChartDtoList = new ArrayList<>();

    ActiveRechargeChartDto monActiveRechargeChartDto = new ActiveRechargeChartDto();
    monActiveRechargeChartDto.setDay("Mon");
    monActiveRechargeChartDto.setActiveVouchers(2200);
    monActiveRechargeChartDto.setRecharges(1400);
    activeRechargeChartDtoList.add(monActiveRechargeChartDto);

    ActiveRechargeChartDto tueActiveRechargeChartDto = new ActiveRechargeChartDto();
    tueActiveRechargeChartDto.setDay("Tue");
    tueActiveRechargeChartDto.setActiveVouchers(2280);
    tueActiveRechargeChartDto.setRecharges(2198);
    activeRechargeChartDtoList.add(tueActiveRechargeChartDto);

    ActiveRechargeChartDto wedActiveRechargeChartDto = new ActiveRechargeChartDto();
    wedActiveRechargeChartDto.setDay("Wed");
    wedActiveRechargeChartDto.setActiveVouchers(5000);
    wedActiveRechargeChartDto.setRecharges(4300);
    activeRechargeChartDtoList.add(wedActiveRechargeChartDto);

    ActiveRechargeChartDto thuActiveRechargeChartDto = new ActiveRechargeChartDto();
    thuActiveRechargeChartDto.setDay("Thu");
    thuActiveRechargeChartDto.setActiveVouchers(4780);
    thuActiveRechargeChartDto.setRecharges(2908);
    activeRechargeChartDtoList.add(thuActiveRechargeChartDto);

    ActiveRechargeChartDto friActiveRechargeChartDto = new ActiveRechargeChartDto();
    friActiveRechargeChartDto.setDay("Fri");
    friActiveRechargeChartDto.setActiveVouchers(5890);
    friActiveRechargeChartDto.setRecharges(4800);
    activeRechargeChartDtoList.add(friActiveRechargeChartDto);

    ActiveRechargeChartDto satActiveRechargeChartDto = new ActiveRechargeChartDto();
    satActiveRechargeChartDto.setDay("Sat");
    satActiveRechargeChartDto.setActiveVouchers(4390);
    satActiveRechargeChartDto.setRecharges(3800);
    activeRechargeChartDtoList.add(satActiveRechargeChartDto);

    ActiveRechargeChartDto sunActiveRechargeChartDto = new ActiveRechargeChartDto();
    sunActiveRechargeChartDto.setDay("Sun");
    sunActiveRechargeChartDto.setActiveVouchers(4490);
    sunActiveRechargeChartDto.setRecharges(4300);
    activeRechargeChartDtoList.add(sunActiveRechargeChartDto);

    return new ResponseEntity<>(activeRechargeChartDtoList, HttpStatus.OK);

  }

}
