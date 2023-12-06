package ru.n08i40k.airdrops.config.main;

import com.google.common.base.Charsets;
import com.google.common.primitives.Chars;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.n08i40k.airdrops.AirDrops;
import ru.n08i40k.airdrops.locale.Locale;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class SignalTemplateEntryAccessor {
    public static ItemStack getSignal(SignalTemplateEntry signalTemplateEntry) {
        ItemStack itemStack = new ItemStack(signalTemplateEntry.getMaterial());

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(AirDrops.getInstance().getLocale().get("item.signal.default"));
        itemStack.setItemMeta(meta);

        itemStack.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("airdrop", true);
        nbtItem.setBoolean("Unbreakable", true);

        return nbtItem.getItem();
    }

    public static ItemStack getSignal(SignalTemplateEntry signalTemplateEntry, String name) {
        ItemStack itemStack = getSignal(signalTemplateEntry);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(AirDrops.getInstance().getLocale().get("item.signal.custom"));
        itemStack.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("airdrop-type", name);

        return nbtItem.getItem();
    }
}
