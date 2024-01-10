package itis.second_sem_work.files.game.items;

public interface U_turn {
    public enum HorDirection {
        LEFT(-1), RIGHT(1);

        private final int sign;

        private HorDirection(final int sign) {
            this.sign = sign;
        }

        public int getSign() {
            return sign;
        }
    }

    public HorDirection getHorDirection();

    public void setHorDirection(HorDirection horDirection);
}