package com.optiva.topup.voms.batch.scheduledbatch.voucherpolicy.deletedvoucherdestroyingpolicy;

import static com.optiva.topup.voms.common.support.WithVoucherPolicyTopicsSupport.DELETED_VOUCHER_DESTROYING_POLICY_TASK_TOPIC;
import static com.optiva.topup.voms.common.types.FileManagerConfigParameterType.ARCHIVE_FILES_PATH;

import com.optiva.topup.voms.batch.lanuchers.VoucherPolicyJobLauncher;
import com.optiva.topup.voms.batch.utils.CustomJobParameter;
import com.optiva.topup.voms.common.entities.voucherpolicies.VoucherPolicySchedule;
import com.optiva.topup.voms.common.entities.vouchers.DeletedVoucher;
import com.optiva.topup.voms.common.entities.vouchers.DestroyedVoucher;
import com.optiva.topup.voms.common.repositories.configparameters.FileManagerConfigParameterRepo;
import com.optiva.topup.voms.common.utils.FileUtil;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DeletedVoucherDestroyingPolicyLauncher extends VoucherPolicyJobLauncher {

  private final DeletedVoucherDestroyingPolicyJobListener jobListener;
  private final DeletedVoucherDestroyingPolicyItemWriteListener
      itemWriteListener;
  private final DeletedVoucherDestroyingPolicyItemProcess itemProcess;
  private final DeletedVoucherArchiveItemWriterStream itemWriterStream;
  private final FileManagerConfigParameterRepo configParameterRepo;
  private final FileUtil fileUtil;

  @Autowired
  public DeletedVoucherDestroyingPolicyLauncher(
      DeletedVoucherDestroyingPolicyJobListener jobListener,
      DeletedVoucherDestroyingPolicyItemWriteListener itemWriteListener,
      DeletedVoucherDestroyingPolicyItemProcess itemProcess,
      DeletedVoucherArchiveItemWriterStream itemWriterStream,
      FileManagerConfigParameterRepo configParameterRepo, FileUtil fileUtil) {
    this.jobListener = jobListener;
    this.itemWriteListener = itemWriteListener;
    this.itemProcess = itemProcess;
    this.itemWriterStream = itemWriterStream;
    this.configParameterRepo = configParameterRepo;
    this.fileUtil = fileUtil;
  }

  @KafkaListener(topics = DELETED_VOUCHER_DESTROYING_POLICY_TASK_TOPIC)
  @Override
  public void onMessage(Integer policyId) {
    launch(policyId);
  }

  @Override
  protected JobParametersBuilder buildJobParameters(VoucherPolicySchedule voucherPolicySchedule) {

    final String archiveFileExtension = ".arch";
    final String archiveFileNameDateFormat = "yyyyMMddHHmmss";
    final String resourceParamName = "archiveFileResource";

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(archiveFileNameDateFormat);
    String formattedDate = LocalDateTime.now().format(dateTimeFormatter);

    String archiveFileName = formattedDate + "." + archiveFileExtension;

    String archiveFilesPath = configParameterRepo.findByParameter(ARCHIVE_FILES_PATH)
        .getValue();

    File archiveFile = fileUtil.createFile(archiveFilesPath, archiveFileName);

    Resource archiveFileResource = new FileSystemResource(archiveFile);

    CustomJobParameter<Resource> archiveFileResourceParameter = new CustomJobParameter<>(
        archiveFileResource);

    JobParametersBuilder jobParametersBuilder = super.buildJobParameters(voucherPolicySchedule);
    jobParametersBuilder.addParameter(resourceParamName, archiveFileResourceParameter);
    return jobParametersBuilder;
  }

  @Override
  protected Job job(VoucherPolicySchedule voucherPolicySchedule) {
    return jobBuilderFactory
        .get(getJobName())
        .start(archive())
        .next(step(voucherPolicySchedule))
        .listener(jobListener)
        .build();
  }

  @Override
  protected Step step(VoucherPolicySchedule voucherPolicySchedule) {

    final String stepReader = "DeletedVoucherDestroyingPolicyStep";
    final String readerName = "DeletedVoucherDestroyingPolicyReader";

    JpaPagingItemReader<DeletedVoucher> itemReader =
        jobBuilderUtils.readAllVouchers(readerName, DeletedVoucher.class);
    JpaItemWriter<DestroyedVoucher> itemWriter = jobBuilderUtils.buildJpaItemWriter();

    return jobBuilderUtils.buildStep(stepReader, itemReader, itemWriter,
        itemProcess, itemWriteListener);
  }

  private Step archive() {

    final String stepName = "DeletedVoucherDestroyingPolicyArchive";
    final String readerName = "DeletedVoucherDestroyingPolicyReader";

    String itemReaderSql =
        "select v from DeletedVoucher v order by v.serialNumber Asc";

    JpaPagingItemReader<DeletedVoucher> itemReader =
        jobBuilderUtils.buildJpaItemReader(readerName, itemReaderSql);

    return jobBuilderUtils.buildStep(stepName, itemReader, itemWriterStream);
  }

}
