package seermod.cards;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import seermod.character.SEER;
import seermod.powers.*;
import seermod.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class 背弃圣灵 extends BaseCard{
    public static final String ID = makeID("Abandon Sacredness");
    private static final int BASE_EXHAUSTIVE = 1;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            AbstractCard.CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            AbstractCard.CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public 背弃圣灵() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(p, new VerticalAuraEffect(Color.BLACK, p.hb.cX, p.hb.cY), 0.33F));
        addToBot(new SFXAction("ATTACK_FIRE"));
        addToBot(new VFXAction(p, new VerticalAuraEffect(Color.PURPLE, p.hb.cX, p.hb.cY), 0.33F));
        addToBot(new VFXAction(p, new VerticalAuraEffect(Color.CYAN, p.hb.cX, p.hb.cY), 0.0F));
        addToBot(new VFXAction(p, new BorderLongFlashEffect(Color.MAGENTA), 0.0F, true));

        if (player.hasPower(谱尼元素.POWER_ID)){
            addToTop(new RemoveSpecificPowerAction(player, player, 谱尼元素.POWER_ID));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 7), 7));
        }

        if (player.hasPower(谱尼圣洁.POWER_ID)){
            addToTop(new RemoveSpecificPowerAction(player, player, 谱尼圣洁.POWER_ID));
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 7), 7));
        }

        if (player.hasPower(谱尼封印.POWER_ID)){
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, player.getPower(谱尼封印.POWER_ID).amount), player.getPower(谱尼封印.POWER_ID).amount));
            addToTop(new RemoveSpecificPowerAction(player, player, 谱尼封印.POWER_ID));
        }

        if (!player.hasPower(混沌魔域.POWER_ID)){
            addToBot(new ApplyPowerAction(p, p, new 混沌魔域(p)));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

}
