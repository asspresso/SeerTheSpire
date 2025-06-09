package seermod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import seermod.character.SEER;
import static seermod.SeerMod.makeID;

public class 秋风的讯息 extends BaseRelic {
    private static final String NAME = "Message of Autumn"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private boolean used = true;

    public 秋风的讯息() {
        super(ID, NAME, SEER.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public void atPreBattle() {
        used = false;
    }

    public void atTurnStart() {
        if (!used) {
            AbstractPlayer p = AbstractDungeon.player;
            int cnt = countPlayerDebuffs(p);
            if (cnt >= 2) {
                flash();
                addToBot(new RemoveDebuffsAction(p));
                addToTop(new ApplyPowerAction(p, p, new BufferPower(p, 1), 1));
                used = true;
                grayscale = true;
            }
        }
    }

    private int countPlayerDebuffs(AbstractPlayer p) {
        int debuffCount = 0;

        for (AbstractPower q : p.powers) {
            if (q.type == AbstractPower.PowerType.DEBUFF) {
                debuffCount += 1;
            }
        }
        return debuffCount;
    }

    public void justEnteredRoom(AbstractRoom room) {
        grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}


