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
    	return prefs.get("activationekey", "null");
    }
    
    public static void insertActivationKey(String key)
    {
    	prefs.put("activationekey", key);
    }
    
    public static String getLicense()
    {
    	return prefs.get("license", "null");
    }
    
    public static void insertLicense(String license)
    {
    	prefs.put("license", license);
    }
    
    public static boolean getMensnummerBox() {
        return prefs.get("mensnummerbox", "true").equals("true");
    }

    public static void setMensnummerBox(String mensnummerBox) {
        prefs.put("mensnummerbox", mensnummerBox);
    }

    public static boolean getBSNBox() {
        return prefs.get("bsnbox", "true").equals("true");
    }

    public static void setBSNBox(String bsnBox) {
        prefs.put("bsnbox", bsnBox);
    }

    public static boolean getGebBox() {
        return prefs.get("gebbox", "true").equals("true");
    }

    public static void setGebBox(String gebBox) {
        prefs.put("gebbox", gebBox);
    }

    public static boolean getWoonverbandBox() {
        return prefs.get("woonverbandbox", "true").equals("true");
    }

    public static void setWoonverbandBox(String woonverbandBox) {
        prefs.put("woonverbandbox", woonverbandBox);
    }

    public static boolean getOldAdresBox() {
        return prefs.get("oldadresbox", "true").equals("true");
    }

    public static void setOldAdresBox(String oldAdresBox) {
        prefs.put("oldadresbox", oldAdresBox);
    }

    public static boolean getNewAdresBox() {
        return prefs.get("newadresbox", "true").equals("true");
    }

    public static void setNewAdresBox(String newAdresBox) {
        prefs.put("newadresbox", newAdresBox);
    }

    public static void setCheckBox(String checkBox, String state) {
        prefs.put(checkBox, state);
    }

    public static String getUserID() {
        return prefs.get("userid", "");
    }

    public static void setUserID(String userID) {
        prefs.put("userid", userID);
    }
}