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

public class 冻伤 extends BasePower {
    private AbstractCreature source;
    public static final String POWER_ID = makeID("Cold");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public 冻伤(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    public void updateDescription() {
        int damage = calculateColdDamage();
        if (owner != null && !owner.isPlayer) {
            if (player.hasPower(谱尼元素.POWER_ID) && owner != player) {
                description = DESCRIPTIONS[1] + DESCRIPTIONS[2] + damage + DESCRIPTIONS[3];
            } else {
                description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + damage + DESCRIPTIONS[3];
            }
        } else {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[4] + damage + DESCRIPTIONS[3];
        }
    }

    private int calculateColdDamage() {
        if (player.hasPower(自然之力.POWER_ID) && owner != player) {
            return 3 + player.getPower(自然之力.POWER_ID).amount;
        } else {
            return 3;
        }
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            int damage = calculateColdDamage();
            addToBot(new DamageAction(owner, new DamageInfo(owner, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    public void atEndOfRound() {
        if (amount == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, 冻伤.POWER_ID));
        } else {
            addToBot(new ReducePowerAction(owner, owner, 冻伤.POWER_ID, 1));
        }
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            if (player.hasPower(谱尼元素.POWER_ID)){
                return damage * 1.65F;
            } else {
                return damage * 1.35F;
            }
        } else {
            return damage;
        }
    }
}