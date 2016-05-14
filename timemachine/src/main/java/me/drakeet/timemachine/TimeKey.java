package me.drakeet.timemachine;

/**
 * The entrance of time machine SDK
 *
 * @author drakeet
 */
public class TimeKey {

    public static String appName;
    public static String userId;


    private TimeKey() throws IllegalAccessException {
        throw new IllegalAccessException();
    }


    /**
     * Initialize the time machine SDK
     *
     * @param appName your app name
     * @param userId your current user uuid.
     */
    public static void install(String appName, String userId) {
        TimeKey.appName = appName;
        TimeKey.userId = userId;
    }


    public static boolean isCurrentUser(String userId) {
        if (TimeKey.userId == null || userId == null) {
            return false;
        } else {
            return TimeKey.userId.equals(userId);
        }
    }
}
