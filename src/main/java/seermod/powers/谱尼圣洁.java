package seermod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static seermod.SeerMod.makeID;

public class 谱尼圣洁 extends BasePower {
    public static final String POWER_ID = makeID("Pure Pony");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public 谱尼圣洁(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
