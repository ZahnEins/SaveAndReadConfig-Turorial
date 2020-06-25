package me.zahneins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Zahneins extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getConfig().getDefaults();
        saveConfig();
        Bukkit.getServer().getConsoleSender().sendMessage("ก็มาดิจารย์");
        //สร้างไอเทมขึ้นมาหนึ่งชิ้น แล้วบันทึกลง config
        //itemstack = ชนิดไอเทมและข้อมูลต่างๆ
        ItemStack stack = new ItemStack(Material.DIAMOND);
        //รับไอเทมเมต้าคือข้อมูลของไอเทม stack
        ItemMeta itemMeta = stack.getItemMeta();
        assert itemMeta != null;
        //list of string มันก็คือลิสของสตริง
        List<String> list = new ArrayList<String>();
        list.add("lore 1");
        list.add("lore 2");
        list.add("lore 3");
        list.add("lore 4");
        //ตั้งชื่อให้ไอเทม
        itemMeta.setDisplayName("TestItem");
        //เอนช้านให้ไอเทม
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL,20,true);
        //ใส่ customModeldataได้
        itemMeta.setCustomModelData(80);
        //ใส่Flags ได้จะซ่อนเอนช้าน ปรับFlags ต่างๆ
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        //เสร็จก็เอา itemmeta ยัดกลับเข้าไปใน itemstack
        stack.setItemMeta(itemMeta);
        //รับไฟล์ config แล้วset(ชื่อ,object) >> itemstack ก็คือ object
        getConfig().set("TestItem",stack);
        //เสร็จแล้วบันทึก
        saveConfig();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        //คอมมาน อธิบายสั้นๆได้ว่า args.lenght มันคือความยาวอะ แบบยังไงดี /getitem 1 2 3 << อันนี้คือ .lengt มี args กี่ตัวสมมุติ /getitem show = length จำนวน 1
        //args[n] << ตรงนี้มันจะเริ่มจาก 0 ในขณะที่ args.length จะเริ่มจาก 1 ไม่งงนะ
        //args[n] = ตำแหน่งนั้นๆ เช่น /getitem test ถ้าเราเรียกใช้ args[0] มันก็จะไปดึงค่า test มาจบ!!
        if(command.getName().equals("getitem")){
            //เช็คว่ามี args ไหมถ้าไม่มีคือรีเทิน ไม่มีในที่นี้คือ /getitem อย่างเดียว
            if(args.length <= 0){
                return true;
            }
            //เช็คว่าเท่ากับ 1 ไหม คือ /getitem show
            else if(args.length == 1){
               if(args[0].equals("show")){
                   //โชว์ข้อมูลไอทมในมือของผู้เล่น
                    player.sendMessage(ChatColor.AQUA + "ItemData : " + ChatColor.GREEN + player.getInventory().getItemInMainHand());
               }
               else {
                   return true;
               }
                return true;
            }
            //ถ้านอกเหนือจากนี้ก็จะมาอยู่กระจุกนี้
            else {
                if(args[0].equals("get")){
                    //เพิ่มไอเทมเข้าคลังโดยรับจาก getconfig().getItemstack(args[1]) ในส่วนนี้คือชื่อของ path ตอนที่เราบันทึกหรือที่มีใน config
                    player.getInventory().addItem(getConfig().getItemStack(args[1]));
                    player.sendMessage(ChatColor.GREEN + "ได้รับไอเทมจาก config");
                    return true;
                }
                else if(args[0].equals("save")){
                    //อันนี้บันทึกไอเทมลงconfig ไอเทมในมือนะ โดยใช้ชื่อมาเป็น String path
                    getConfig().set(args[1],player.getInventory().getItemInMainHand());
                    saveConfig();
                    player.sendMessage(ChatColor.GREEN + "บันทึกไอเทมลง config");
                    return true;
                }
                else {
                    return true;
                }
            }

        }
        return true;
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
