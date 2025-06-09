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

public class 烧伤 extends BasePower {
    private int number = 2;
    private AbstractCreature source;
    public static final String POWER_ID = makeID("Burn");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public 烧伤(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    public void updateDescription() {
        int damage = calculateBurnDamage();
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

    private int calculateBurnDamage() {
        if (player.hasPower(自然之力.POWER_ID) && owner != player) {
            return 3 + player.getPower(自然之力.POWER_ID).amount;
        } else {
            return 3;
        }
    }

    public void atStartOfTurn() {
        int damage = calculateBurnDamage();
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            addToBot(new DamageAction(owner, new DamageInfo(owner, damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    public void atEndOfRound() {
        if (amount == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, 烧伤.POWER_ID));
        } else {
            addToBot(new ReducePowerAction(owner, owner, 烧伤.POWER_ID, 1));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            if (player.hasPower(谱尼元素.POWER_ID)){
                return damage * 0.6F;
            } else {
                return damage * 0.75F;
            }
        } else {
            return damage;
        }
    }

}