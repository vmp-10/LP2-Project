package core;

public enum Event {
    DROP_LANDED,
    WEAPON_FOUND,
    TRAP_FOUND,
    ENEMY_FOUND,
    ITEM_FOUND,
    OUT_OF_SAFE_ZONE,
    GET_SHOT_AT,
    NOTHING;

    public static Event[] getCityEvents() {
        return new Event[]{WEAPON_FOUND, TRAP_FOUND, ENEMY_FOUND, ITEM_FOUND, OUT_OF_SAFE_ZONE, GET_SHOT_AT};
    }
}


