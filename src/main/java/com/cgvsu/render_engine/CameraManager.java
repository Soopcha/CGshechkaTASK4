package com.cgvsu.render_engine;

import java.util.ArrayList;
import java.util.List;

public class CameraManager {
    private List<Camera> cameras;
    private int currentCameraIndex;

    public CameraManager() {
        cameras = new ArrayList<>();
        currentCameraIndex = 0;
    }

    public void addCamera(Camera camera) {
        cameras.add(camera);
    }

    public void removeCamera(int index) {
        if (index >= 0 && index < cameras.size()) {
            cameras.remove(index);
        }
    }

    public void switchCamera(int index) {
        if (index >= 0 && index < cameras.size()) {
            currentCameraIndex = index;
        }
    }

    public Camera getCurrentCamera() {
        if (cameras.isEmpty()) {
            return null;
        }
        return cameras.get(currentCameraIndex);
    }

    public int getNumCameras() {
        return cameras.size();
    }
}
