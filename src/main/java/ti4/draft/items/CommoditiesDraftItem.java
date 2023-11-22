package ti4.draft.items;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import ti4.draft.DraftItem;
import ti4.generator.Mapper;
import ti4.helpers.Emojis;
import ti4.model.FactionModel;

public class CommoditiesDraftItem extends DraftItem {
    public CommoditiesDraftItem(String itemId) {
        super(Category.COMMODITIES, itemId);
    }

    private FactionModel getFaction() {
        if (ItemId.equals("keleres")) {
            return Mapper.getFaction("keleresa");
        }
        return Mapper.getFaction(ItemId);
    }

    @Override
    public MessageEmbed getItemCard() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(getItemEmoji() + getItemName());

        eb.addField("Commodities:", String.valueOf(getFaction().getCommodities()), true);

        return eb.build();
    }

    @Override
    public String getItemName() {
        return getFaction().getFactionName() + " Commodities";
    }

    @Override
    public String getItemEmoji() {
        return Emojis.comm;
    }
}
