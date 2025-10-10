package spark;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.scheduler.*;
import org.apache.spark.sql.SparkSession;

public class SparkApplicationListener extends SparkListener {

  static Class<EventPublisher> publisherClass;

  public static void setPublisherClass(Class<EventPublisher> publisherClass) {
    SparkApplicationListener.publisherClass = publisherClass;
  }

  private EventPublisher publisher;

  public void publishMessage(ListenerEvent event) {
    if (publisher == null) {
      return;
    }
    publisher.publish(event);
  }

  public interface EventPublisher {

    void publish(ListenerEvent event);
  }

  static final String CONFIG_KEY = "spark.extraListeners";

  public static void register(JavaSparkContext jsc) {
    jsc.getConf()
      .set(CONFIG_KEY, SparkApplicationListener.class.getName());
  }

  public static void register(SparkContext sc) {
    sc.getConf()
      .set(CONFIG_KEY, SparkApplicationListener.class.getName());
  }

  public static void register(SparkSession.Builder builder) {
    builder
      .config(CONFIG_KEY, SparkApplicationListener.class.getName());
  }

  private String appId;
  private String appName;

  private ListenerEvent createEvent(String eventName) {
    ListenerEvent event = new ListenerEvent();
    event.setName(eventName);
    event.setAppId(appId);
    event.setAppName(appName);
    event.setCreatedTimestamp(String.valueOf(System.currentTimeMillis()));
    return event;
  }

  @Override
  public void onApplicationEnd(SparkListenerApplicationEnd applicationEnd) {
    super.onApplicationEnd(applicationEnd);
    ListenerEvent event = createEvent("onApplicationEnd");
    publishMessage(event);
  }

  @Override
  public void onApplicationStart(SparkListenerApplicationStart applicationStart) {
    super.onApplicationStart(applicationStart);
    appId = applicationStart.appId().get();
    appName = applicationStart.appName();
    ListenerEvent event = createEvent("onApplicationStart");
    publishMessage(event);
  }

  @Override
  public void onBlockManagerAdded(SparkListenerBlockManagerAdded blockManagerAdded) {
    super.onBlockManagerAdded(blockManagerAdded);
    ListenerEvent event = createEvent("onBlockManagerAdded");
    publishMessage(event);
  }

  @Override
  public void onBlockManagerRemoved(SparkListenerBlockManagerRemoved blockManagerRemoved) {
    super.onBlockManagerRemoved(blockManagerRemoved);
    ListenerEvent event = createEvent("onBlockManagerRemoved");
    publishMessage(event);
  }

  @Override
  public void onBlockUpdated(SparkListenerBlockUpdated blockUpdated) {
    super.onBlockUpdated(blockUpdated);
    ListenerEvent event = createEvent("onBlockUpdated");
    publishMessage(event);
  }

  @Override
  public void onEnvironmentUpdate(SparkListenerEnvironmentUpdate environmentUpdate) {
    super.onEnvironmentUpdate(environmentUpdate);
    ListenerEvent event = createEvent("onEnvironmentUpdate");
    publishMessage(event);
  }

  @Override
  public void onExecutorAdded(SparkListenerExecutorAdded executorAdded) {
    super.onExecutorAdded(executorAdded);
    ListenerEvent event = createEvent("onExecutorAdded");
    publishMessage(event);
  }

  @Override
  public void onExecutorBlacklisted(SparkListenerExecutorBlacklisted executorBlacklisted) {
    super.onExecutorBlacklisted(executorBlacklisted);
    ListenerEvent event = createEvent("onExecutorBlacklisted");
    publishMessage(event);
  }

  @Override
  public void onExecutorBlacklistedForStage(SparkListenerExecutorBlacklistedForStage executorBlacklistedForStage) {
    super.onExecutorBlacklistedForStage(executorBlacklistedForStage);
    ListenerEvent event = createEvent("onExecutorBlacklistedForStage");
    publishMessage(event);
  }

  @Override
  public void onExecutorExcluded(SparkListenerExecutorExcluded executorExcluded) {
    super.onExecutorExcluded(executorExcluded);
    ListenerEvent event = createEvent("onExecutorExcluded");
    publishMessage(event);
  }

  @Override
  public void onExecutorExcludedForStage(SparkListenerExecutorExcludedForStage executorExcludedForStage) {
    super.onExecutorExcludedForStage(executorExcludedForStage);
    ListenerEvent event = createEvent("onExecutorExcludedForStage");
    publishMessage(event);
  }

  @Override
  public void onExecutorMetricsUpdate(SparkListenerExecutorMetricsUpdate executorMetricsUpdate) {
    super.onExecutorMetricsUpdate(executorMetricsUpdate);
    ListenerEvent event = createEvent("onExecutorMetricsUpdate");
    publishMessage(event);
  }

  @Override
  public void onExecutorRemoved(SparkListenerExecutorRemoved executorRemoved) {
    super.onExecutorRemoved(executorRemoved);
    ListenerEvent event = createEvent("onExecutorRemoved");
    publishMessage(event);
  }

  @Override
  public void onExecutorUnblacklisted(SparkListenerExecutorUnblacklisted executorUnblacklisted) {
    super.onExecutorUnblacklisted(executorUnblacklisted);
    ListenerEvent event = createEvent("onExecutorUnblacklisted");
    publishMessage(event);
  }

  @Override
  public void onExecutorUnexcluded(SparkListenerExecutorUnexcluded executorUnexcluded) {
    super.onExecutorUnexcluded(executorUnexcluded);
    ListenerEvent event = createEvent("onExecutorUnexcluded");
    publishMessage(event);
  }

  @Override
  public void onJobEnd(SparkListenerJobEnd jobEnd) {
    super.onJobEnd(jobEnd);
    ListenerEvent event = createEvent("onJobEnd");
    publishMessage(event);
  }

  @Override
  public void onJobStart(SparkListenerJobStart jobStart) {
    super.onJobStart(jobStart);
    ListenerEvent event = createEvent("onJobStart");
    publishMessage(event);
  }

  @Override
  public void onNodeBlacklisted(SparkListenerNodeBlacklisted nodeBlacklisted) {
    super.onNodeBlacklisted(nodeBlacklisted);
    ListenerEvent event = createEvent("onNodeBlacklisted");
    publishMessage(event);
  }

  @Override
  public void onNodeBlacklistedForStage(SparkListenerNodeBlacklistedForStage nodeBlacklistedForStage) {
    super.onNodeBlacklistedForStage(nodeBlacklistedForStage);
    ListenerEvent event = createEvent("onNodeBlacklistedForStage");
    publishMessage(event);
  }

  @Override
  public void onNodeExcluded(SparkListenerNodeExcluded nodeExcluded) {
    super.onNodeExcluded(nodeExcluded);
    ListenerEvent event = createEvent("onNodeExcluded");
    publishMessage(event);
  }

  @Override
  public void onNodeExcludedForStage(SparkListenerNodeExcludedForStage nodeExcludedForStage) {
    super.onNodeExcludedForStage(nodeExcludedForStage);
    ListenerEvent event = createEvent("onNodeExcludedForStage");
    publishMessage(event);
  }

  @Override
  public void onNodeUnblacklisted(SparkListenerNodeUnblacklisted nodeUnblacklisted) {
    super.onNodeUnblacklisted(nodeUnblacklisted);
    ListenerEvent event = createEvent("onNodeUnblacklisted");
    publishMessage(event);
  }

  @Override
  public void onNodeUnexcluded(SparkListenerNodeUnexcluded nodeUnexcluded) {
    super.onNodeUnexcluded(nodeUnexcluded);
  }

  @Override
  public void onOtherEvent(SparkListenerEvent event) {
    super.onOtherEvent(event);
  }

  @Override
  public void onResourceProfileAdded(SparkListenerResourceProfileAdded event) {
    super.onResourceProfileAdded(event);

  }

  @Override
  public void onSpeculativeTaskSubmitted(SparkListenerSpeculativeTaskSubmitted speculativeTask) {
    super.onSpeculativeTaskSubmitted(speculativeTask);
  }

  @Override
  public void onStageCompleted(SparkListenerStageCompleted stageCompleted) {
    super.onStageCompleted(stageCompleted);
  }

  @Override
  public void onStageExecutorMetrics(SparkListenerStageExecutorMetrics executorMetrics) {
    super.onStageExecutorMetrics(executorMetrics);
  }

  @Override
  public void onStageSubmitted(SparkListenerStageSubmitted stageSubmitted) {
    super.onStageSubmitted(stageSubmitted);
  }

  @Override
  public void onTaskEnd(SparkListenerTaskEnd taskEnd) {
    super.onTaskEnd(taskEnd);
  }

  @Override
  public void onTaskGettingResult(SparkListenerTaskGettingResult taskGettingResult) {
    super.onTaskGettingResult(taskGettingResult);
  }

  @Override
  public void onTaskStart(SparkListenerTaskStart taskStart) {
    super.onTaskStart(taskStart);
  }

  @Override
  public void onUnpersistRDD(SparkListenerUnpersistRDD unpersistRDD) {
    super.onUnpersistRDD(unpersistRDD);
  }

  @Override
  public void onUnschedulableTaskSetAdded(SparkListenerUnschedulableTaskSetAdded unschedulableTaskSetAdded) {
    super.onUnschedulableTaskSetAdded(unschedulableTaskSetAdded);
  }

  @Override
  public void onUnschedulableTaskSetRemoved(SparkListenerUnschedulableTaskSetRemoved unschedulableTaskSetRemoved) {
    super.onUnschedulableTaskSetRemoved(unschedulableTaskSetRemoved);
    ListenerEvent event = createEvent("onUnschedulableTaskSetRemoved");
    publishMessage(event);
  }
}
