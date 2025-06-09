package seermod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static seermod.SeerMod.makeID;

public class 禁赛 extends BasePower {
    public static final String POWER_ID = makeID("Banned");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public 禁赛(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (amount >= 3) {
            addToBot(new LoseHPAction(owner, owner, 99999, AbstractGameAction.AttackEffect.FIRE));
            amount -= 3;
            if (amount <= 0) {
                addToTop(new RemoveSpecificPowerAction(owner, owner, 禁赛.POWER_ID));
            }
        }

    }
}