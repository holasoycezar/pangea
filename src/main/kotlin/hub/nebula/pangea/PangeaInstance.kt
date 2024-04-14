package hub.nebula.pangea

import dev.minn.jda.ktx.jdabuilder.light
import hub.nebula.pangea.api.command.PangeaCommandManager
import hub.nebula.pangea.configuration.GeneralConfig.PangeaConfig
import hub.nebula.pangea.listener.vanilla.PangeaReadyEvent
import hub.nebula.pangea.listener.vanilla.PangeaSlashCommandEvent
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.DiscordLocale
import net.dv8tion.jda.api.interactions.commands.localization.ResourceBundleLocalizationFunction

class PangeaInstance(
    val config: PangeaConfig
) {
    lateinit var jda: JDA
    lateinit var commandManager: PangeaCommandManager

    fun start() {
        jda = light(config.token, enableCoroutines = true)
        val locales = ResourceBundleLocalizationFunction
            .fromBundles(
                "commands",
                DiscordLocale.PORTUGUESE_BRAZILIAN,
                DiscordLocale.ENGLISH_US
            ) // TODO: More localizations
            .build()
        commandManager = PangeaCommandManager(locales, this)
        PangeaSlashCommandEvent(this).handle()
        PangeaReadyEvent(this).handle()
        jda.awaitReady()
    }
}