package ti4.commands.cardsac;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ti4.generator.Mapper;
import ti4.helpers.Constants;
import ti4.helpers.Helper;
import ti4.map.Game;
import ti4.map.Player;
import ti4.message.MessageHelper;

public class RevealAndPutACIntoDiscard extends ACCardsSubcommandData {
    public RevealAndPutACIntoDiscard() {
        super(Constants.REVEAL_AND_PUT_AC_INTO_DISCARD, "Reveal Action Card from deck and put into discard pile");
    }
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Game activeGame = getActiveGame();
        Player player = activeGame.getPlayer(getUser().getId());
        player = Helper.getGamePlayer(activeGame, player, event, null);
        if (player == null) {
            MessageHelper.sendMessageToChannel(event.getChannel(), "Player could not be found");
            return;
        }
        String acID = activeGame.drawActionCardAndDiscard();
        StringBuilder sb = new StringBuilder();
        sb.append("Game: ").append(activeGame.getName()).append(" ");
        sb.append("Player: ").append(player.getUserName()).append("\n");
        sb.append("Revealed and discarded Action card: ");
        sb.append(Mapper.getActionCard(acID).getRepresentation()).append("\n");
        MessageHelper.sendMessageToChannel(event.getMessageChannel(), sb.toString());
    }
}
