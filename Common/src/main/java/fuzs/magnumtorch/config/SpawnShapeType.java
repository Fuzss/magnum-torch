package fuzs.magnumtorch.config;

import net.minecraft.core.BlockPos;

public enum SpawnShapeType {
    ELLIPSOID {

        @Override
        public boolean isPositionContained(int posX, int posY, int posZ, int width, int height) {
            return (posX * posX + posZ * posZ) / (float) (width * width) + (posY * posY) / (float) (height * height) <= 1.0;
        }
    }, CYLINDER {

        @Override
        public boolean isPositionContained(int posX, int posY, int posZ, int width, int height) {
            return posX * posX + posZ * posZ <= width * width && posY <= height;
        }
    }, CUBOID {

        @Override
        public boolean isPositionContained(int posX, int posY, int posZ, int width, int height) {
            return posX <= width && posZ <= width && posY <= height;
        }
    };

    public boolean isPositionContained(BlockPos pos, BlockPos toCheck, int width, int height) {
        int posX = Math.abs(pos.getX() - toCheck.getX());
        int posY = Math.abs(pos.getY() - toCheck.getY());
        int posZ = Math.abs(pos.getZ() - toCheck.getZ());
        return this.isPositionContained(posX, posY, posZ, width, height);
    }

    public abstract boolean isPositionContained(int posX, int posY, int posZ, int width, int height);
}
