package genius.module.impl.movement;

import genius.Client;
import genius.event.impl.EventMove;
import genius.event.impl.EventUpdate;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Options;
import genius.module.data.Setting;
import genius.module.impl.combat.TargetStrafe;
import genius.util.MovementUtils;
import me.hippo.api.lwjeb.annotation.Handler;

public class Flight extends Module {
    public final static String MODE = "MODE";
    private String MOTIONSPEED = "MOTIONSPEED";
    int stage;
    boolean shouldSpeed;
    private float motionSpeed = ((Number)settings.get(MOTIONSPEED).getValue()).floatValue();

    public Flight(ModuleData data) {
        super(data);
        settings.put(MODE, new Setting<>(MODE, new Options("Fly Mode", "Cube", new String[]{"Motion", "Cube"}), "Fly method."));
        settings.put(MOTIONSPEED, new Setting<>(MOTIONSPEED, 1.5, "Motion speed.", 0.01, 0.0, 5.0));
    }

    @Handler
    public void onUpdate(EventUpdate event) {
        double speed = 0;
        if (event.isPre()) {
            setSuffix(((Options) settings.get(MODE).getValue()).getSelected());
            double X = mc.thePlayer.posX;
            double Y = mc.thePlayer.posY;
            double Z = mc.thePlayer.posZ;
            switch (((Options) settings.get(MODE).getValue()).getSelected()) {
                case "Cube":
                    if (!isOnGround(0.001)) {
                        stage++;
                        mc.timer.timerSpeed = 0.27f;
                        mc.thePlayer.lastReportedPosY = 0;
                        mc.thePlayer.jumpMovementFactor = 0;
                        mc.thePlayer.onGround = false;
                        double motion = mc.thePlayer.motionY;
                        speed = 0;
                        event.setGround(false);
                        if (stage == 1) {
                            boolean a = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, 0.4d, 0.0D)).isEmpty();

                            event.setY(Y + (a ? 0.4 : 0.2));
                            motion = 0.4;
                        } else if (stage == 2) {
                            motion = 0.28;
                            boolean a = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, 0.68d, 0.0D)).isEmpty();
                            event.setY(Y + (a ? 0.68 : 0.2));
                            if (shouldSpeed)
                                speed = 2.4;
                            else {
                                shouldSpeed = true;
                                speed = 2.2;
                            }
                        } else if (stage == 3) {
                            motion = -0.68;
                        } else if (stage == 4) {
                            motion = 0;
                            speed = 2.4;
                            stage = 0;
                        }

                        setMotion(speed);
                        mc.thePlayer.motionY = 0;
                    } else if (shouldSpeed) {
                        setMotion(0);

                        shouldSpeed = !shouldSpeed;
                    } else if (mc.timer.timerSpeed != 1) {
                        mc.timer.timerSpeed = 1f;
                    }
                    break;
            }
        }
    }

    @Handler
    public void onMove(EventMove event) {
        TargetStrafe targetStrafe = (TargetStrafe) Client.getModuleManager().get(TargetStrafe.class);
        switch (((Options) settings.get(MODE).getValue()).getSelected()) {
            case "Motion":
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.thePlayer.motionY = 0.15F;
                } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.thePlayer.motionY = -0.15F;
                } else {
                    event.setY(mc.thePlayer.motionY = 0F);
                }

                if (!targetStrafe.doStrafeAtSpeed(event, this.motionSpeed)) {
                    MovementUtils.setSpeed(event, this.motionSpeed);
                }
                break;
        }
    }

    public static void setMotion(double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    public static boolean isOnGround(double height) {
        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        setMotion(0.2);
        mc.thePlayer.jumpMovementFactor = 0;
        mc.thePlayer.capabilities.isFlying = false;
        mc.timer.timerSpeed = 1f;
        mc.thePlayer.capabilities.allowFlying = false;
    }
}
