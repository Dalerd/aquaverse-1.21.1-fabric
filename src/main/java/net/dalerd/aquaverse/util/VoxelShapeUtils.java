package net.dalerd.aquaverse.util;

import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class VoxelShapeUtils {

    /**
     * Rotate a VoxelShape around Y axis by 90 degrees clockwise.
     */
    public static VoxelShape rotateY90(VoxelShape shape) {
        return rotateY(shape, 90);
    }

    /**
     * Rotate a VoxelShape around Y axis by 180 degrees.
     */
    public static VoxelShape rotateY180(VoxelShape shape) {
        return rotateY(shape, 180);
    }

    /**
     * Rotate a VoxelShape around Y axis by 270 degrees clockwise.
     */
    public static VoxelShape rotateY270(VoxelShape shape) {
        return rotateY(shape, 270);
    }

    /**
     * Helper that actually rotates the shape's boxes.
     */
    private static VoxelShape rotateY(VoxelShape shape, int angle) {
        VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};

        int times = (angle / 90) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
                // Y rotation: (x, z) → (z, 1 - x)
                VoxelShape newBox = VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX);
                buffer[1] = VoxelShapes.union(buffer[1], newBox);
            });
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }
        return buffer[0];
    }
}


