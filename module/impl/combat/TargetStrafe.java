package genius.module.impl.combat;

import genius.Client;
import genius.event.impl.EventKeyPress;
import genius.event.impl.EventMove;
import genius.event.impl.EventRender3D;
import genius.module.Module;
import genius.module.data.ModuleData;
import genius.module.data.Setting;
import genius.util.MovementUtils;
import genius.util.RotationUtils;
import jdk.nashorn.internal.ir.BreakNode;
import me.hippo.api.lwjeb.annotation.Handler;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glPopMatrix;

public class TargetStrafe extends Module {
    private String RADIUS = "RADIUS";
    private String RENDER = "RENDER";
    private String DIRECTIONKEYS = "DIRECTIONKEYS";

    private int direction = -1;

    public TargetStrafe(ModuleData data) {
        super(data);
        settings.put(RADIUS, new Setting<>(RADIUS, 2.0, "Radius.", 0.01, 0.1, 4.0));
        settings.put(RENDER, new Setting<>(RENDER, true, "Rendering circle of target."));
        settings.put(DIRECTIONKEYS, new Setting<>(DIRECTIONKEYS, true, "DirectionKeys uses."));
        setDisplayName("Target Strafe");
    }

    @Handler
    public void onKeyPress(EventKeyPress event) {
        if ((((Boolean) settings.get(DIRECTIONKEYS).getValue()))) {
            switch (event.getKey()) {
                case Keyboard.KEY_D:
                    direction = -1;
                    break;
                case Keyboard.KEY_A:
                    direction = 1;
                    break;
            }
        }
    }

    @Handler
    public final void onMove(EventMove event) {
        if (event.isPre()) {
            if (mc.thePlayer.isCollidedHorizontally) {
                switchDirection();
            }
        }
    }

    private void switchDirection() {
        if (direction == 1) {
            direction = -1;
        } else {
            direction = 1;
        }
    }

    public final boolean doStrafeAtSpeed(EventMove event, final double moveSpeed) {
        final boolean strafe = canStrafe();
        if (strafe) {
            KillAura aura = (KillAura) Client.getModuleManager().get(KillAura.class);
            float[] rotations = RotationUtils.getNeededRotations(aura.getOptimalTarget());
            float radius = (int) ((Number) settings.get(RADIUS).getValue()).floatValue();
            if (mc.thePlayer.getDistanceToEntity(aura.getOptimalTarget()) <= radius) {
                MovementUtils.setSpeed(event, moveSpeed, rotations[0], direction, 0);
            } else {
                MovementUtils.setSpeed(event, moveSpeed, rotations[0], direction, 1);
            }
        }
        return strafe;
    }

    @Handler
    public final void onRender3D(EventRender3D event) {
        if (canStrafe() && (((Boolean) settings.get(RENDER).getValue()))) {
            float radius = (int) ((Number) settings.get(RADIUS).getValue()).floatValue();
            KillAura aura = (KillAura) Client.getModuleManager().get(KillAura.class);
            drawCircle(aura.getOptimalTarget(), event.getPartialTicks(), radius);
        }
    }

    private void drawCircle(Entity entity, float partialTicks, double rad) {
        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_POLYGON_SMOOTH);
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(1.0f);
        glBegin(GL_LINE_STRIP);

        final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
        final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
        final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

        final float r = ((float) 1 / 255) * Color.WHITE.getRed();
        final float g = ((float) 1 / 255) * Color.WHITE.getGreen();
        final float b = ((float) 1 / 255) * Color.WHITE.getBlue();

        final double pix2 = Math.PI * 2.0D;

        for (int i = 0; i <= 90; ++i) {
            glColor3f(r, g, b);
            glVertex3d(x + rad * Math.cos(i * pix2 / 45.0), y, z + rad * Math.sin(i * pix2 / 45.0));
        }

        glEnd();
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_POLYGON_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glPopMatrix();
    }

    public boolean canStrafe() {
        KillAura aura = (KillAura) Client.getModuleManager().get(KillAura.class);
        return aura.isEnabled() && aura.getOptimalTarget() != null && this.isEnabled();
    }
}
