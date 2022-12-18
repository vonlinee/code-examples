package io.devpl.fxsdk;

public class MainViewController implements ControlledStage {

    private StageController controller;

    @Override
    public void setStageController(StageController stageController) {
        this.controller = stageController;
    }
}
