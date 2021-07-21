package genius.management;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ColorManager {
    public static List<ColorObject> getColorObjectList() {
        return colorObjectList;
    }

    private static List<ColorObject> colorObjectList = new CopyOnWriteArrayList<>();

    public ColorObject getHudColor() {
        return hudColor;
    }

    public static ColorObject hudColor = new ColorObject(0,192,255,255);

    public ColorObject getEspColor() {
        return espColor;
    }

    public static ColorObject espColor = new ColorObject(255,0,0,255);

    public ColorObject getCrosshairColor() {
        return crossHairColor;
    }

    public static ColorObject crossHairColor = new ColorObject(255,0,0,255);

    public ColorManager() {
        colorObjectList.add(hudColor);
        colorObjectList.add(espColor);
        colorObjectList.add(crossHairColor);
    }
}