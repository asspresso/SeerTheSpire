package seermod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static seermod.SeerMod.makeID;

public class 致命一击 extends BasePower {
    private AbstractCreature source;
    public static final String POWER_ID = makeID("CriticalHit");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;


    public 致命一击(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (owner.hasPower(谱尼圣洁.POWER_ID)) {
            description = DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0];
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK){
            if (owner.hasPower(暴乱.POWER_ID)){
                addToBot(new DrawCardAction(owner, owner.getPower(暴乱.POWER_ID).amount));
            }
            addToBot(new ReducePowerAction(owner, owner, 致命一击.POWER_ID, 1));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (owner.hasPower(谱尼圣洁.POWER_ID)) {
            return type == DamageInfo.DamageType.NORMAL ? damage * 2.0F : damage;
        } else {
            return type == DamageInfo.DamageType.NORMAL ? damage * 1.5F : damage;
        }
    }
}