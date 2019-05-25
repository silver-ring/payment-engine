package com.optiva.topup.voms.batch.lanuchers;

import static com.optiva.topup.voms.common.types.FileManagerConfigParameterType.LIST_ORDER_FILES_PATH;

import com.optiva.topup.voms.batch.utils.CustomJobParameter;
import com.optiva.topup.voms.common.entities.orders.ListOrder;
import com.optiva.topup.voms.common.repositories.configparameters.FileManagerConfigParameterRepo;
import java.io.File;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public abstract class ListOrderJobLauncher<T extends ListOrder> extends BatchOrderJobLauncher<T> {

  @Autowired
  private FileManagerConfigParameterRepo fileManagerConfigParameterRepo;

  protected JobParametersBuilder buildJobParameters(T listOrder) {

    final String orderFileResourceParameterName = "orderFileResource";

    JobParametersBuilder jobParametersBuilder = super.buildJobParameters(listOrder);
    String fileName = listOrder.getFileName();
    String filePath = fileManagerConfigParameterRepo
        .findByParameter(LIST_ORDER_FILES_PATH)
        .getValue();
    File file = new File(filePath + fileName);
    Resource orderFileResource = new FileSystemResource(file);
    CustomJobParameter<Resource> orderFileResourceParameter = new CustomJobParameter<>(
        orderFileResource);
    jobParametersBuilder.addParameter(orderFileResourceParameterName, orderFileResourceParameter);
    return jobParametersBuilder;
  }

}
