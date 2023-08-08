package board.tiles;

import static board.tiles.Powerup.DEFAULT_POWERUP_DURATION;

public enum PowerupType {
    SLOW_DOWN_ENEMIES(
        () -> {},
        DEFAULT_POWERUP_DURATION,
        () -> {}
    ),

    SPEED_UP_PLAYER(
        () -> {},
        DEFAULT_POWERUP_DURATION,
        () -> {}       
    ),

    INVINCIBILITY(
        () -> {},
        DEFAULT_POWERUP_DURATION,
        () -> {}       
    ),

    FREEZE_ENEMIES(
        () -> {},
        DEFAULT_POWERUP_DURATION,
        () -> {}       
    ),

    REDUCE_PLAYER_COOLDOWN(
        () -> {},
        DEFAULT_POWERUP_DURATION,
        () -> {}       
    ),
    
    INCREASE_FIREBALL_EFFECT(
        () -> {},
        DEFAULT_POWERUP_DURATION,
        () -> {}       
    );

    double durationTimeSeconds;

    PowerupAction collisionAction;
    PowerupAction durationTimeoutAction;

    PowerupType(PowerupAction collisionAction, double durationTimeSeconds, PowerupAction durationTimeoutAction) {
        this.durationTimeSeconds = durationTimeSeconds;
        this.collisionAction = collisionAction;
        this.durationTimeoutAction = durationTimeoutAction;
    }
}
