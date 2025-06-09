package seermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static seermod.SeerMod.makeID;

public class 星皇之赐 extends BasePower {
    public static final String POWER_ID = makeID("Emperor");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public 星皇之赐(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atStartOfTurn() {
        this.flash();
        addToBot(new DrawCardAction(owner, 1));
        addToBot(new ApplyPowerAction(owner, owner, new ArtifactPower(owner, 1), 1));
        addToBot(new ReducePowerAction(owner, owner, 星皇之赐.POWER_ID, 1));
    }

}