package seermod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import seermod.character.SEER;

import static seermod.SeerMod.makeID;

public class 通用刻印激活水晶 extends BaseRelic {
    private static final String NAME = "Activation Crystal"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private boolean firstTurn = true;

    public 通用刻印激活水晶() {
        super(ID, NAME, SEER.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public void atPreBattle() {
        firstTurn = true;
    }

    public void atTurnStart() {
        if (firstTurn) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ArmamentsAction(true));
            firstTurn = false;
            grayscale = true;
        }
    }

    public void justEnteredRoom(AbstractRoom room) {
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
