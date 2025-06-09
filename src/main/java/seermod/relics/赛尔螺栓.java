package seermod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import seermod.character.SEER;
import seermod.powers.中毒;
import seermod.powers.冻伤;
import seermod.powers.烧伤;

import static seermod.SeerMod.makeID;

public class 赛尔螺栓 extends BaseRelic {
    private static final String NAME = "Bolt"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private boolean used = true;

    public 赛尔螺栓() {
        super(ID, NAME, SEER.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public void onPlayerEndTurn() {
        String[] powerIDs = {冻伤.POWER_ID, 烧伤.POWER_ID, 中毒.POWER_ID};

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            int count = 0;
            for (String powerID : powerIDs) {
                if (monster.hasPower(powerID)) {
                    count += 1;
                }
            }
            if (count >= 2) {
                addToBot(new ApplyPowerAction(monster, AbstractDungeon.player, new WeakPower(monster, 1, false), 1));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


