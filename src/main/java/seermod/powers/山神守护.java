package seermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static seermod.SeerMod.makeID;

public class 山神守护 extends BasePower {
    public static final String POWER_ID = makeID("Blessing of the Mountain");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public 山神守护(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            addToTop(new ReducePowerAction(owner, owner, 山神守护.POWER_ID, 1));
            addToBot(new ApplyPowerAction(info.owner, owner, new 中毒(info.owner, owner, 3), 3));
            addToBot(new ApplyPowerAction(info.owner, owner, new 烧伤(info.owner, owner, 3), 3));
            addToBot(new ApplyPowerAction(info.owner, owner, new 冻伤(info.owner, owner, 3), 3));
        }

        return 0;
    }

}