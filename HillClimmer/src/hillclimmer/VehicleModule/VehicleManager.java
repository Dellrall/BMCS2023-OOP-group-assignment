/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.VehicleModule;

/**
 *
 * @author las
 */
public class VehicleManager {
    private String managerID;
    private String AuthorizeLv;
    private String managerName;
    public static int changeCount;
    private int modifierRules;

    public VehicleManager(String managerID, String AuthorizeLv, String managerName, int modifierRules) {
        this.managerID = managerID;
        this.AuthorizeLv = AuthorizeLv;
        this.managerName = managerName;
        this.modifierRules = modifierRules;
    }
    
    
}
