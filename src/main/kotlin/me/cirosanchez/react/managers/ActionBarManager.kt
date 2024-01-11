package me.cirosanchez.react.managers


import me.cirosanchez.react.React
import me.cirosanchez.react.config.impl.ActionBarConfig
import me.cirosanchez.react.util.CC
import me.cirosanchez.react.util.Logger
import me.clip.placeholderapi.PlaceholderAPI
import net.kyori.adventure.sound.Sound
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitTask

data class Actionbar(var msg: String, val sound: Sound?, val particle: Particle?)
data class RecurrentActionbar(var msg: String, val sound: Sound?, val particle: Particle?, val permission: String, val time: Long)

class ActionBarManager(val plugin: React) : Listener {

    private val config: ActionBarConfig = plugin.actionBarConfig
    private val joinActionbars: MutableList<Actionbar> = mutableListOf()
    private val actionbars: MutableList<Actionbar> = mutableListOf()
    private val recurrentActionBars: MutableList<RecurrentActionbar> = mutableListOf()
    private val runnables: HashMap<BukkitTask, Runnable> = hashMapOf()

    init {
        loadActionbars()
        loadJoinActionbars()
        loadRecurrentActionBars()
        plugin.server.pluginManager.registerEvents(this, React.instance)
    }

    /**
     * @param player -> Player that will receive the actionbar
     * @param msg -> The message to display, will be colorized on the method.
     * @return
     */
    fun send(player: Player, msg: String){
        player.sendActionBar(CC.colorize(PlaceholderAPI.setPlaceholders(player, msg)));
    }

    /**
     * @param player player that receives the actionbar
     * @param msg the actionbar message
     * @param sound the sound that will play when the actionbar is sent
     * @return
     */
    fun send(player: Player, msg: String, sound: Sound){
        player.playSound(sound)
        player.sendActionBar(CC.colorize(PlaceholderAPI.setPlaceholders(player, msg)))
    }
    /**
     * @param player player that receives the actionbar
     * @param msg the actionbar message
     * @param particle the particle to spawn when the actionbar is sent
     */
    fun send(player: Player, msg: String, particle: Particle){
        player.spawnParticle(particle, player.getLocation(), 1);
        player.sendActionBar(CC.colorize(PlaceholderAPI.setPlaceholders(player, msg)))
    }

    /**
     *  @param player player that receives the actionbar
     *  @param msg the actionbar message
     *  @param sound the sound that will play when the actionbar is sent
     *  @param particle the particle to spawn when the actionbar is sent
     */
    fun send(player: Player, msg: String, sound: Sound, particle: Particle){
        player.spawnParticle(particle, player.getLocation(), 1)
        player.playSound(sound)
        player.sendActionBar(CC.colorize(PlaceholderAPI.setPlaceholders(player, msg)))
    }

    /**
     * @param player player that receives the actionbar
     * @param bar the actionbar object to send
     */
    fun send(player: Player, bar: Actionbar){
        val msg: String = bar.msg
        val sound: Sound? = bar.sound
        val particle: Particle? = bar.particle

        player.sendActionBar(CC.colorize(PlaceholderAPI.setPlaceholders(player, msg)))
        if (sound != null) player.playSound(sound)
        if (particle != null) player.spawnParticle(particle, player.getLocation(), 1)
    }

    /**
     * @param player player that receives the actionbar
     * @param id the id of the loaded actionbar
     */
    fun send(player: Player, id: Int){
        if (actionbars.size <= id-1){
            return
        }
        actionbars[id-1].msg = PlaceholderAPI.setPlaceholders(player, actionbars[id-1].msg)
        send(player, actionbars[id-1])
    }

    /**
     * @param id the id to check
     */
    fun check(id: Int) : Boolean{
        return id <= actionbars.size
    }

    private fun loadActionbars(){
        Logger.log("<gold>Registering Actionbars...</gold>")
        for (ab: String in config.getConfigurationSection("actionbars")!!.getKeys(false)) run {
            val msg: String = config.getString("actionbars.$ab.msg").toString()
            val s: String? = config.getString("actionbars.$ab.sound")
            val p: String? = config.getString("actionbars.$ab.particle")

            val sound: Sound? = if (s != "none"){
                Sound.sound(org.bukkit.Sound.valueOf(s.toString()), Sound.Source.MASTER, 1f, 1f)
            } else {
                null
            }

            val particle: Particle? = if (p != "none"){
                Particle.valueOf(p.toString())
            } else {
                null
            }

            val bar: Actionbar = Actionbar(msg, sound, particle)
            actionbars.add(bar)

            Logger.log("<gold>Registered Actionbar with ID:</gold><green> $ab</green>")
        }
    }
    private fun loadJoinActionbars(){
        Logger.log("<gold>Registering Join Actionbars...</gold>")
        for (ab: String in config.getConfigurationSection("join-actionbars")!!.getKeys(false)) run {
            val msg: String = config.getString("join-actionbars.$ab.msg").toString()
            val s: String? = config.getString("join-actionbars.$ab.sound")
            val p: String? = config.getString("join-actionbars.$ab.particle")

            val sound: Sound? = if (s != "none"){
                Sound.sound(org.bukkit.Sound.valueOf(s.toString()), Sound.Source.MASTER, 1f, 1f)
            } else {
                null
            }

            val particle: Particle? = if (p != "none"){
                Particle.valueOf(p.toString())
            } else {
                null
            }

            val bar: Actionbar = Actionbar(msg, sound, particle)
            joinActionbars.add(bar)

            Logger.log("<gold>Registered Join Actionbar with ID:</gold><green> $ab</green>")
        }
    }


    /*
    Listening purposes
     */
    @EventHandler
    fun join(event: PlayerJoinEvent){
        if (!config.getBoolean("features.join-actionbars")){
            return
        }
        val bar: Actionbar = joinActionbars.get(joinActionbars.indices.random())
        bar.msg = PlaceholderAPI.setPlaceholders(event.player, bar.msg)
        send(event.player, bar)
    }


    /*
    Recurrent Actionbars
     */
    fun loadRecurrentActionBars(){
        Logger.log("<gold>Registering Recurrent Actionbars...</gold>")
        for (ab: String in config.getConfigurationSection("recurrent-actionbars")!!.getKeys(false)) run {
            val msg: String = config.getString("recurrent-actionbars.$ab.msg").toString()
            val s: String? = config.getString("recurrent-actionbars.$ab.sound")
            val p: String? = config.getString("recurrent-actionbars.$ab.particle")
            val perm: String = config.getString("recurrent-actionbars.$ab.permission").toString()
            val time: Long = config.getLong("recurrent-actionbars.$ab.time")

            val sound: Sound? = if (s != "none"){
                Sound.sound(org.bukkit.Sound.valueOf(s.toString()), Sound.Source.MASTER, 1f, 1f)
            } else {
                null
            }

            val particle: Particle? = if (p != "none"){
                Particle.valueOf(p.toString())
            } else {
                null
            }

            val bar = RecurrentActionbar(msg, sound, particle, perm, time)
            recurrentActionBars.add(bar)

            Logger.log("<gold>Registered Recurrent Actionbar with ID:</gold><green>$ab</green>")
        }
        Logger.log("<gold>Finished registering Recurrent Actionbars!")
        Logger.log("<gold>Starting Recurrent Actionbars tasks...")
        for (bar: RecurrentActionbar in recurrentActionBars){
            val runnable = Runnable {
                for (player: Player in Bukkit.getOnlinePlayers()){
                    send(player, Actionbar(bar.msg, bar.sound, bar.particle))
                }
            }

            val task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, 10L, bar.time)
            runnables.put(task, runnable)
        }
        Logger.log("<gold>Finished Recurrent Actionbars Tasks...")
    }

    /**
     * Reload purposes
     */
    fun unload(){
        for (task in runnables.keys){
            plugin.server.scheduler.cancelTask(task.taskId)
        }
    }
}