package arachnid.objects;

import arachnid.util.Transform;

import java.util.ArrayList;
import java.util.List;

public class WorldObject {

    private Transform transform;

    private List<Object> objects = new ArrayList<>();

    public WorldObject() {
        transform = new Transform();
    }

    public void attach(Object object) {
        objects.add(object);
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public void draw() {
        for (Object obj : objects) {
            obj.draw(this);
        }
    }

    public void destroy() {
        for (Object obj : objects) {
            obj.destroy();
        }
    }

}
