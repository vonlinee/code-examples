package org.mybatis.generator;

import org.mybatis.generator.api.ProgressCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since created on 2022年9月12日
 */
public class DefaultProgressCallback implements ProgressCallback {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void introspectionStarted(int totalTasks) {
		log.info("total tasks => {}", totalTasks);
	}

	@Override
	public void generationStarted(int totalTasks) {
		log.info("generationStarted => {}", totalTasks);
	}

	@Override
	public void saveStarted(int totalTasks) {
		log.info("saveStarted => {}", totalTasks);
	}

	@Override
	public void startTask(String taskName) {
		log.info("startTask => {}", taskName);
	}

	@Override
	public void done() {
		log.info("done");
	}

	@Override
	public void checkCancel() throws InterruptedException {
		log.info("checkCancel");
	}
}
