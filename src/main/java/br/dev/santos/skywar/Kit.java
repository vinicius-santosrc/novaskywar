package br.dev.santos.skywar;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Kit implements Listener {

    // Array para armazenar os kits
    private static List<KitInterface> kits = new ArrayList<>();

    // Classe interna que define a interface do kit
    public static class KitInterface {
        String name;
        Boolean haveItems;
        ItemStack item;
        Boolean exclusiveVip;
        String hability;

        public KitInterface(String name, Boolean haveItems, ItemStack item, Boolean exclusiveVip, String hability) {
            this.name = name;
            this.haveItems = haveItems;
            this.item = item;
            this.exclusiveVip = exclusiveVip;
            this.hability = hability;
        }

        public String getName() {
            return name;
        }

        public ItemStack getItem() {
            return item;
        }

        public Boolean isExclusiveVip() {
            return exclusiveVip;
        }

        public String getHability() {
            return hability;
        }
    }

    public static void addKit(KitInterface kit) {
        kits.add(kit);
    }

    public static void giveItems(Player player, String kitName) {
        for (KitInterface kit : kits) {
            if (kit.getName().equalsIgnoreCase(kitName)) {
                ItemStack item = new ItemStack(kit.getItem());
                ItemMeta itemMeta = item.getItemMeta();

                item.setItemMeta(itemMeta);

                Inventory inventory = player.getInventory();
                inventory.addItem(item);

                break;
            }
        }
    }

    public static void initializeKits() {
        ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL);
        ItemStack ironchest = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack nullItem = new ItemStack(Material.AIR);
        ItemStack construtor = new ItemStack(Material.STONE, 32);
        ItemStack esquimo = new ItemStack(Material.SNOW_BALL, 10);
        ItemStack homemdascavernas = new ItemStack(Material.FLINT_AND_STEEL);
        ItemStack arqueiro = new ItemStack(Material.BOW);
            arqueiro.addEnchantment(Enchantment.ARROW_FIRE, 1);
        ItemStack homemFogo = new ItemStack(Material.WOOD_SWORD);
            homemFogo.addEnchantment(Enchantment.FIRE_ASPECT, 1);
        ItemStack sacerdoteVida = new ItemStack(Material.POTION, 3); // Representa poções de vida
        ItemStack sacerdoteRegen = new ItemStack(Material.POTION, 3); // Representa poções de regeneração
        ItemStack homemBomba = new ItemStack(Material.TNT, 3);
        ItemStack encantador = new ItemStack(Material.EXP_BOTTLE, 5);
        ItemStack minerador = new ItemStack(Material.IRON_PICKAXE);
        ItemStack zeus = new ItemStack(Material.BLAZE_ROD, 3);
        ItemStack pesoPena = new ItemStack(Material.IRON_BOOTS);
            pesoPena.addEnchantment(Enchantment.PROTECTION_FALL, 4);
        ItemStack poseidon = new ItemStack(Material.WATER_BUCKET, 5);
        ItemStack apple = new ItemStack(Material.GOLDEN_APPLE, 3);
        ItemStack grandpa = new ItemStack(Material.GOLD_SPADE);
            grandpa.addEnchantment(Enchantment.DURABILITY, 1);
        ItemStack craftingTable = new ItemStack(Material.WORKBENCH);
        ItemStack furnace = new ItemStack(Material.FURNACE);
        ItemStack stoneAxe = new ItemStack(Material.STONE_AXE);
        ItemStack stonePickaxe = new ItemStack(Material.STONE_PICKAXE);
        ItemStack woodenShovel = new ItemStack(Material.WOOD_SPADE);
        ItemStack homemAranha = new ItemStack(Material.WEB, 5);
        ItemStack theFlash = new ItemStack(Material.POTION, 1); // Representa a poção de velocidade
        ItemStack ninjaChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
            ninjaChestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP, 3);

        addKit(new KitInterface("Enderman", true, enderpearl, true, "Enderman"));
        addKit(new KitInterface("Paladino", true, ironchest, false, ""));
        addKit(new KitInterface("Vida-extra", false, nullItem, true, "Vida-extra"));
        addKit(new KitInterface("Construtor", true, construtor, false, ""));
        addKit(new KitInterface("Esquimo", true, esquimo, false, ""));
        addKit(new KitInterface("Homem-das-Cavernas", true, homemdascavernas, false, ""));
        addKit(new KitInterface("Arqueiro", true, arqueiro, false, ""));

        addKit(new KitInterface("Homem-Fogo", true, homemFogo, false, ""));
        addKit(new KitInterface("Sacerdote", true, sacerdoteVida, false, ""));
        addKit(new KitInterface("Sacerdote", true, sacerdoteRegen, false, ""));
        addKit(new KitInterface("Homem-Bomba", true, homemBomba, false, ""));
        addKit(new KitInterface("Vampiro", false, nullItem, true, "Vampiro"));
        addKit(new KitInterface("Encantador", true, encantador, false, ""));
        addKit(new KitInterface("Minerador", true, minerador, false, ""));
        addKit(new KitInterface("Zeus", true, zeus, false, ""));
        addKit(new KitInterface("Peso-Pena", true, pesoPena, false, ""));
        addKit(new KitInterface("Poseidon", true, poseidon, false, ""));
        addKit(new KitInterface("Apple", true, apple, false, ""));
        addKit(new KitInterface("GrandPa", true, grandpa, false, ""));
        addKit(new KitInterface("Ferramentas", true, craftingTable, false, ""));
        addKit(new KitInterface("Ferramentas", true, furnace, false, ""));
        addKit(new KitInterface("Ferramentas", true, stoneAxe, false, ""));
        addKit(new KitInterface("Ferramentas", true, stonePickaxe, false, ""));
        addKit(new KitInterface("Ferramentas", true, woodenShovel, false, ""));
        addKit(new KitInterface("Homem-Aranha", true, homemAranha, false, ""));
        addKit(new KitInterface("The-Flash", true, theFlash, false, ""));
        addKit(new KitInterface("Ninja", true, ninjaChestplate, false, ""));
        addKit(new KitInterface("Sopa", true, sopa, false, ""));
        addKit(new KitInterface("Assassino", false, nullItem, true, "Assassino"));
    }

}
