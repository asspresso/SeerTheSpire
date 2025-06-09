package seermod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import seermod.character.SEER;

import static seermod.SeerMod.makeID;

public class 无敌精灵胶囊 extends BaseRelic {
    private static final String NAME = "Superior Capsule"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public 无敌精灵胶囊() {
        super(ID, NAME, SEER.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public void atBattleStartPreDraw() {
        this.flash();
        addToBot(new seermod.actions.精灵胶囊(true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(精灵胶囊.ID);
    }

    public void obtain() {
        if (AbstractDungeon.player.hasRelic(精灵胶囊.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(精灵胶囊.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }
}


