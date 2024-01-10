package itis.second_sem_work.files.game.items;

public record Vectors(double x, double y) {
    @Override
    public String toString() {
        return "Vector2D [x=" + x + ", y=" + y + "]";
    }
}