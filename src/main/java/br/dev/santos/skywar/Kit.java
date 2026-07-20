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
        ItemStack icon;
        Boolean exclusiveVip;
        String hability;

        public KitInterface(String name, Boolean haveItems, ItemStack item, ItemStack icon, Boolean exclusiveVip, String hability) {
            this.name = name;
            this.haveItems = haveItems;
            this.item = item;
            this.icon = icon;
            this.exclusiveVip = exclusiveVip;
            this.hability = hability;
        }

        public String getName() {
            return name;
        }

        public ItemStack getItem() {
            return item;
        }

        public ItemStack getIcon() {
            return icon;
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

        // Icon ItemStacks (matching swkits.yml IDs)
        ItemStack iconEnderman = new ItemStack(Material.ENDER_PEARL, 1);
        ItemStack iconPaladino = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack iconVidaExtra = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1); // 322:1
        ItemStack iconConstrutor = new ItemStack(Material.STONE, 1);
        ItemStack iconEsquimo = new ItemStack(Material.SNOW_BALL, 1);
        ItemStack iconHomemDasCavernas = new ItemStack(Material.FLINT_AND_STEEL, 1);
        ItemStack iconArqueiro = new ItemStack(Material.BOW, 1); // 261
        ItemStack iconHomemFogo = new ItemStack(Material.WOOD_SWORD, 1); // 268
        ItemStack iconSacerdote = new ItemStack(Material.POTION, 1); // 373
        ItemStack iconHomemBomba = new ItemStack(Material.TNT, 1); // 46
        ItemStack iconVampiro = new ItemStack(Material.POTION, 1, (short) 1); // 373:1
        ItemStack iconEncantador = new ItemStack(Material.ENCHANTMENT_TABLE, 1); // 116
        ItemStack iconMinerador = new ItemStack(Material.IRON_PICKAXE, 1); // 257
        ItemStack iconZeus = new ItemStack(Material.BLAZE_ROD, 1); // 369
        ItemStack iconPesoPena = new ItemStack(Material.FEATHER, 1); // 288
        ItemStack iconPoseidon = new ItemStack(Material.WATER_BUCKET, 1); // 326
        ItemStack iconApple = new ItemStack(Material.GOLDEN_APPLE, 1); // 322
        ItemStack iconGrandpa = new ItemStack(Material.GOLD_SPADE, 1); // 284
        ItemStack iconFerramentas = new ItemStack(Material.WORKBENCH, 1); // 58
        ItemStack iconFerramentasFurnace = new ItemStack(Material.FURNACE, 1);
        ItemStack iconFerramentasAxe = new ItemStack(Material.STONE_AXE, 1);
        ItemStack iconFerramentasPickaxe = new ItemStack(Material.STONE_PICKAXE, 1);
        ItemStack iconFerramentasShovel = new ItemStack(Material.WOOD_SPADE, 1);
        ItemStack iconHomemAranha = new ItemStack(Material.WEB, 1); // 30
        ItemStack iconTheFlash = new ItemStack(Material.POTION, 1, (short) 2); // 373:2
        ItemStack iconNinja = new ItemStack(Material.WOOL, 1, (short) 15); // 35:15
        ItemStack iconSopa = new ItemStack(Material.MUSHROOM_SOUP, 1); // 282
        ItemStack iconAssassino = new ItemStack(Material.DROPPER, 1); // 401

        addKit(new KitInterface("Enderman", true, enderpearl, iconEnderman, true, "Enderman"));
        addKit(new KitInterface("Paladino", true, ironchest, iconPaladino, false, ""));
        addKit(new KitInterface("Vida-extra", false, nullItem, iconVidaExtra, true, "Vida-extra"));
        addKit(new KitInterface("Construtor", true, construtor, iconConstrutor, false, ""));
        addKit(new KitInterface("Esquimo", true, esquimo, iconEsquimo, false, ""));
        addKit(new KitInterface("Homem-das-Cavernas", true, homemdascavernas, iconHomemDasCavernas, false, ""));
        addKit(new KitInterface("Arqueiro", true, arqueiro, iconArqueiro, false, ""));
        addKit(new KitInterface("Homem-Fogo", true, homemFogo, iconHomemFogo, false, ""));
        addKit(new KitInterface("Sacerdote", true, sacerdoteVida, iconSacerdote, false, ""));
        addKit(new KitInterface("Sacerdote", true, sacerdoteRegen, iconSacerdote, false, ""));
        addKit(new KitInterface("Homem-Bomba", true, homemBomba, iconHomemBomba, false, ""));
        addKit(new KitInterface("Vampiro", false, nullItem, iconVampiro, true, "Vampiro"));
        addKit(new KitInterface("Encantador", true, encantador, iconEncantador, false, ""));
        addKit(new KitInterface("Minerador", true, minerador, iconMinerador, false, ""));
        addKit(new KitInterface("Zeus", true, zeus, iconZeus, false, ""));
        addKit(new KitInterface("Peso-Pena", true, pesoPena, iconPesoPena, false, ""));
        addKit(new KitInterface("Poseidon", true, poseidon, iconPoseidon, false, ""));
        addKit(new KitInterface("Apple", true, apple, iconApple, false, ""));
        addKit(new KitInterface("GrandPa", true, grandpa, iconGrandpa, false, ""));
        addKit(new KitInterface("Ferramentas", true, craftingTable, iconFerramentas, false, ""));
        addKit(new KitInterface("Ferramentas", true, furnace, iconFerramentasFurnace, false, ""));
        addKit(new KitInterface("Ferramentas", true, stoneAxe, iconFerramentasAxe, false, ""));
        addKit(new KitInterface("Ferramentas", true, stonePickaxe, iconFerramentasPickaxe, false, ""));
        addKit(new KitInterface("Ferramentas", true, woodenShovel, iconFerramentasShovel, false, ""));
        addKit(new KitInterface("Homem-Aranha", true, homemAranha, iconHomemAranha, false, ""));
        addKit(new KitInterface("The-Flash", true, theFlash, iconTheFlash, false, ""));
        addKit(new KitInterface("Ninja", true, ninjaChestplate, iconNinja, false, ""));
        addKit(new KitInterface("Sopa", true, sopa, iconSopa, false, ""));
        addKit(new KitInterface("Assassino", false, nullItem, iconAssassino, true, "Assassino"));
    }

    public static ItemStack getKitIcon(String kitName) {
        for (KitInterface kit : kits) {
            if (kit.getName().equalsIgnoreCase(kitName)) {
                return new ItemStack(kit.getIcon());
            }
        }
        return null;
    }
}