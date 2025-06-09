package seermod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
import static seermod.SeerMod.makeID;

public class 中毒 extends BasePower {
    private AbstractCreature source;
    public static final String POWER_ID = makeID("SeerPoison");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public 中毒(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    public void updateDescription() {
        int damage = calculatePoisonDamage();
        if (owner != null && !owner.isPlayer) {
            description = DESCRIPTIONS[0] + damage + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[2] + damage + DESCRIPTIONS[1];
        }
    }

    private int calculatePoisonDamage() {
        if (player.hasPower(自然之力.POWER_ID) && owner != player) {
            return amount + player.getPower(自然之力.POWER_ID).amount;
        } else {
            return amount;
        }
    }

    private void applyPoisonDamage() {
        flashWithoutSound();
        int damage = calculatePoisonDamage();
        addToTop(new DamageAction(owner, new DamageInfo(owner, damage, DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    private void reducePoisonPower() {
        if (amount == 0) {
            addToTop(new RemoveSpecificPowerAction(owner, owner, 中毒.POWER_ID));
        } else {
            addToTop(new ReducePowerAction(owner, owner, 中毒.POWER_ID, 1));
        }
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            applyPoisonDamage();
        }
    }

    public void atEndOfRound() {
        reducePoisonPower();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            flash();
            applyPoisonDamage();
            reducePoisonPower();
        }
        return damageAmount;
    }


}