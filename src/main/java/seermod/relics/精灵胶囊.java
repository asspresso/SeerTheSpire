package seermod.relics;

import seermod.character.SEER;
import static seermod.SeerMod.makeID;

public class 精灵胶囊 extends BaseRelic {
    private static final String NAME = "Primary Capsule"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public 精灵胶囊() {
        super(ID, NAME, SEER.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public void atBattleStartPreDraw() {
        this.flash();
        addToBot(new seermod.actions.精灵胶囊(false));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


