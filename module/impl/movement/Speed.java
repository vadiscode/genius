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
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

import java.util.List;

public class Speed extends Module {
    private String MODE = "MODE";

    public Speed(ModuleData data) {
        super(data);
        settings.put(MODE, new Setting<>(MODE, new Options("Mode", "Bhop", new String[]{"SlowHop", "Bhop"}), "Bypass method."));
    }

    private double speed;
    private double lastDist;
    public static int stage;

    public static double defaultSpeed() {
        double baseSpeed = 0.2873D;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
        }
        return baseSpeed;
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1;
    }

    @Handler
    public void onMove(EventMove event) {
        TargetStrafe targetStrafe = (TargetStrafe) Client.getModuleManager().get(TargetStrafe.class);
        setSuffix(((Options) settings.get(MODE).getValue()).getSelected());
        switch (((Options) settings.get(MODE).getValue()).getSelected()) {
            case "Bhop":
                if ((mc.thePlayer.moveForward == 0.0F) && (mc.thePlayer.moveStrafing == 0.0F)) {
                    speed = defaultSpeed();
                }
                if ((stage == 1) && (mc.thePlayer.isCollidedVertically) && ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F)))
                {
                    speed = (0.25D + defaultSpeed() - 0.01D);
                }
                else if ((stage == 2) && (mc.thePlayer.isCollidedVertically) && ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F)))
                {
                    mc.thePlayer.motionY = 0.4D;
                    event.setY(0.4D);
                    speed *= 2.149D;
                }
                else if (stage == 3)
                {
                    double difference = 0.66D * (this.lastDist - defaultSpeed());
                    speed = (this.lastDist - difference);
                }
                else
                {
                    List collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0D, mc.thePlayer.motionY, 0.0D));
                    if ((collidingList.size() > 0) || (mc.thePlayer.isCollidedVertically)) {
                        if (stage > 0) {
                            if (1.35D * defaultSpeed() - 0.01D > speed) {
                                stage = 0;
                            } else {
                                stage = (mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F) ? 1 : 0;
                            }
                        }
                    }
                    speed = (this.lastDist - this.lastDist / 159.0D);
                }
                speed = Math.max(speed, defaultSpeed());
                if (stage > 0) {
                    setMotion(event, speed);
                }
                if ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F)) {
                    stage += 1;
                }
                if (!targetStrafe.doStrafeAtSpeed(event, speed)) {
                    MovementUtils.setSpeed(event, this.speed);
                }
                break;
            case "SlowHop":
                switch (stage) {
                    case 0:
                        ++stage;
                        lastDist = 0.0D;
                        break;
                    case 2:
                        double motionY = 0.4025;
                        if ((mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) && mc.thePlayer.onGround) {
                            if (mc.thePlayer.isPotionActive(Potion.jump))
                                motionY += ((mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                            event.setY(mc.thePlayer.motionY = motionY);
                            speed *= 2.149;
                        }
                        break;
                    case 3:
                        speed = lastDist - (0.747* (lastDist - defaultSpeed()));
                        break;
                    default:
                        if ((mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) && stage > 0) {
                            stage = mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F ? 0 : 1;
                        }
                        speed = lastDist - lastDist / 159.0D;
                        break;
                }
                speed = Math.max(speed, defaultSpeed());
                double forward = mc.thePlayer.movementInput.moveForward, strafe = mc.thePlayer.movementInput.moveStrafe, yaw = mc.thePlayer.rotationYaw;
                if (forward == 0.0F && strafe == 0.0F) {
                    event.setX(0);
                    event.setZ(0);
                }
                if (forward != 0 && strafe != 0) {
                    forward = forward * Math.sin(Math.PI / 4);
                    strafe = strafe * Math.cos(Math.PI / 4);
                }
                event.setX((forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw))) * 0.99D);
                event.setZ((forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw))) * 0.99D);
                ++stage;
                if (!targetStrafe.doStrafeAtSpeed(event, speed)) {
                    MovementUtils.setSpeed(event, this.speed);
                }
                break;
        }
    }

    @Handler
    public void onUpdate(EventUpdate event) {
        switch (((Options) settings.get(MODE).getValue()).getSelected()) {
            case "Bhop":
                if (event.isPre()) {
                    double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
                    double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
                    lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                }
                break;
            case "SlowHop":
                lastDist = Math.sqrt(((mc.thePlayer.posX - mc.thePlayer.prevPosX) * (mc.thePlayer.posX - mc.thePlayer.prevPosX)) + ((mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * (mc.thePlayer.posZ - mc.thePlayer.prevPosZ)));
                if (lastDist > 5) lastDist = 0.0D;
                break;
        }
    }

    private void setMotion(EventMove em, double speed) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            em.setX(0.0D);
            em.setZ(0.0D);
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
            em.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
            em.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
        }
    }
}