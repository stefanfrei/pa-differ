/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schlibbuz.pa.differ;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stefan Frei <stefan.a.frei@gmail.com>
 */
public class I18n {
    static final Map<String, String> data = new HashMap<>();
    static {
        data.put("aa_missile_vehicle", "Spinner");
        data.put("air_defense", "Galata");
        data.put("air_factory", "Air Factory");
        data.put("air_factory_adv", "Advanced Air Factory");
        data.put("assault_bot", "Dox");
        data.put("assault_bot_adv", "Slammer");
        data.put("attack_vehicle", "Stryker");
        data.put("bot_aa", "Stinger");
        data.put("bot_bomb", "Boombot");
        data.put("bot_factory_adv", "Advanced Bot Factory");
        data.put("bot_sniper", "Gil-E");
        data.put("bot_support_commander", "Colonel");
        data.put("bot_tactical_missile", "Bluehawk");
        data.put("bot_tesla", "Spark");
        data.put("build_metal_cost", "Metal Cost");
        data.put("build_metal_cost", "Metal Cost");
        data.put("damage", "Damage");
        data.put("energy_storage", "Energy Storage");
        data.put("fabrication_bot_combat_adv", "Mend");
        data.put("frigate", "Narwhal");
        data.put("guard_layer", "Guard Layer");
        data.put("gunship", "Kestrel");
        data.put("laser_defense_single", "Single Laser Defense Tower");
        data.put("laser_defense_adv", "Advanced Laser Defense Tower");
        data.put("max_health", "Health");
        data.put("metal_storage", "Metal Storage");
        data.put("metal_extractor_adv", "Advanced Metal Extractor");
        data.put("metal_storage", "Metal Storage");
        data.put("mining_platform", "Jig");
        data.put("naval_factory_adv", "Advanced Naval Factory");
        data.put("orbital_factory", "Orbital Factory");
        data.put("solar_array", "Solar Array");
        data.put("solar_drone", "Icarus");
        data.put("tank_armor", "Vanguard");
        data.put("tank_flak", "Storm");
        data.put("tank_hover", "Drifter");
        data.put("tank_laser_adv", "Leveler");
        data.put("tank_light_laser", "Ant");
        data.put("tank_nuke", "Manhattan");
        data.put("titan_air", "Zeus Titan");
        data.put("titan_bot", "Atlas Titan");
        data.put("titan_vehicle", "Ares Titan");
        data.put("unit_cannon", "Unit Cannon");
        data.put("vehicle_factory_adv", "Advanced Vehicle Factory");
    }

    private I18n() {}

    static String get(String key) {
        if (!data.containsKey(key)) return key;
        return data.get(key);
    }
}
