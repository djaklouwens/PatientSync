package klouwens; //20/4/23 7d

import java.util.prefs.Preferences;

public class UserPreferences {
    private static final Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);

    public static String getLocale() {
        return prefs.get("locale", "nl");
    }

    public static void setLocale(String language) {
        prefs.put("locale", language);
    }

    public static String getLocation() {
        return prefs.get("location", System.getProperty("user.home"));
    }

    public static void setLocation(String location) {
        prefs.put("location", location);
    }
    
    public static String getActivationKey()
    {
    	return prefs.get("activationekey", "no");
    }
    
    public static void insertActivationKey(String key)
    {
    	prefs.put("activationekey", key);
    }
    
    public static String getLicense()
    {
    	return prefs.get("license", "no");
    }
    
    public static void setUserID(String userID)
    {
    	prefs.put("userid", userID);
    }
    
    public static String getUserID()
    {
    	return prefs.get("userid", "");
    }
    

}