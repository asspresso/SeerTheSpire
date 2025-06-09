package seermod.powers;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static seermod.SeerMod.makeID;

public class 轮回之狱 extends BasePower {
    public static final String POWER_ID = makeID("Rebirth");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public 轮回之狱(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onUseCard(AbstractCard c, UseCardAction action) {
        int currentExhaustive = ExhaustiveField.ExhaustiveFields.exhaustive.get(c);
        int baseExhaustive = ExhaustiveField.ExhaustiveFields.baseExhaustive.get(c);
        if (baseExhaustive > 1 && currentExhaustive + 1 == baseExhaustive){
            addToBot(new GainEnergyAction(amount));
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
