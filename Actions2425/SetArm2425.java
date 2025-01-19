package org.firstinspires.ftc.teamcode.Automotons2425.Actions2425;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Automotons2425.ClawArm2425.ClawArm2425;

public class SetArm2425 implements Action2425 {
    private ClawArm2425 arm;
    private double position;
    private double tolerance;
    public SetArm2425 (ClawArm2425 arm, double position, double tolerance) {
        this.arm = arm;
        this.position = position;
        this.tolerance = tolerance;
    }
    /**
     * True if the action has been finished (possibly within reasonable error), false otherwise
     */
    @Override
    public boolean isComplete() {
        return Math.abs(arm.getCurrentPosition() - position) <= tolerance;
    }
    /**
     * Tells the robot to work towards achieving the action
     */
    @Override
    public void doAction() {
        arm.setTargetPosition(position);
        // This is for testing and we'll remove it in a minute
        arm.powerArm(new Telemetry() {
            @Override
            public Item addData(String caption, String format, Object... args) {
                return null;
            }

            @Override
            public Item addData(String caption, Object value) {
                return null;
            }

            @Override
            public <T> Item addData(String caption, Func<T> valueProducer) {
                return null;
            }

            @Override
            public <T> Item addData(String caption, String format, Func<T> valueProducer) {
                return null;
            }

            @Override
            public boolean removeItem(Item item) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public void clearAll() {

            }

            @Override
            public Object addAction(Runnable action) {
                return null;
            }

            @Override
            public boolean removeAction(Object token) {
                return false;
            }

            @Override
            public void speak(String text) {

            }

            @Override
            public void speak(String text, String languageCode, String countryCode) {

            }

            @Override
            public boolean update() {
                return false;
            }

            @Override
            public Line addLine() {
                return null;
            }

            @Override
            public Line addLine(String lineCaption) {
                return null;
            }

            @Override
            public boolean removeLine(Line line) {
                return false;
            }

            @Override
            public boolean isAutoClear() {
                return false;
            }

            @Override
            public void setAutoClear(boolean autoClear) {

            }

            @Override
            public int getMsTransmissionInterval() {
                return 0;
            }

            @Override
            public void setMsTransmissionInterval(int msTransmissionInterval) {

            }

            @Override
            public String getItemSeparator() {
                return "";
            }

            @Override
            public void setItemSeparator(String itemSeparator) {

            }

            @Override
            public String getCaptionValueSeparator() {
                return "";
            }

            @Override
            public void setCaptionValueSeparator(String captionValueSeparator) {

            }

            @Override
            public void setDisplayFormat(DisplayFormat displayFormat) {

            }

            @Override
            public Log log() {
                return null;
            }
        });
    }
    public double getDesiredPosition() {return position;}
}
