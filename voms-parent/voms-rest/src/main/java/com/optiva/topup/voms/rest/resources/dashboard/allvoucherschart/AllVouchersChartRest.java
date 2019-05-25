package com.optiva.topup.voms.rest.resources.dashboard.allvoucherschart;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class AllVouchersChartRest {

  @GetMapping("/allVouchersChart")
  public ResponseEntity<List<AllVouchersChartDto>> getAllVouchersChartData() {

    List<AllVouchersChartDto> allVouchersChartDtos = new ArrayList<>();

    allVouchersChartDtos
        .add(getData("Mon", 489119, 792397, 364852, 13756, 164854, 507816, 678188));

    allVouchersChartDtos
        .add(getData("Tue", 947852, 957027, 990115, 681934, 909072, 891723, 730049));

    allVouchersChartDtos
        .add(getData("Wed", 63171, 546143, 852824, 378713, 547992, 46160, 472584));

    allVouchersChartDtos
        .add(getData("Thu", 78959, 653288, 704314, 33068, 974954, 515607, 944234));

    allVouchersChartDtos
        .add(getData("Fri", 953107, 625206, 667603, 827743, 705336, 483046, 891567));

    allVouchersChartDtos
        .add(getData("Sat", 256490, 14868, 933071, 776288, 65584, 612827, 243238));

    allVouchersChartDtos
        .add(getData("Sun", 698756, 241579, 154713, 183711, 649935, 772816, 132575));

    return new ResponseEntity<>(allVouchersChartDtos, HttpStatus.OK);
  }

  private AllVouchersChartDto getData(String day, int createdVouchersNumber, int activeVouchersNumber,
      int blockedVouchersNumber,
      int pendingUsageVoucherNumber, int userVoucherNumber, int deletedVoucherNumber,
      int archivedVoucherNumber) {
    AllVouchersChartDto monAllVouchersChartDto = new AllVouchersChartDto();
    monAllVouchersChartDto.setDay(day);
    monAllVouchersChartDto.setCreated(createdVouchersNumber);
    monAllVouchersChartDto.setActive(activeVouchersNumber);
    monAllVouchersChartDto.setBlocked(blockedVouchersNumber);
    monAllVouchersChartDto.setPendingUsage(pendingUsageVoucherNumber);
    monAllVouchersChartDto.setUsed(userVoucherNumber);
    monAllVouchersChartDto.setDeleted(deletedVoucherNumber);
    monAllVouchersChartDto.setArchived(archivedVoucherNumber);
    return monAllVouchersChartDto;
  }

}
