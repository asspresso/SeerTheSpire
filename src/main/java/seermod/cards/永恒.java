package seermod.cards;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import seermod.actions.改动次数;
import seermod.character.SEER;
import seermod.powers.混沌魔域;
import seermod.powers.谱尼元素;
import seermod.powers.谱尼圣洁;
import seermod.powers.谱尼封印;
import seermod.util.CardStats;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class 永恒 extends BaseCard {
    public static final String ID = makeID("Eternity");
    private static final int BASE_EXHAUSTIVE = 1;

    private static final CardStats info = new CardStats(
            SEER.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.NONE,
            1
    );

    public 永恒() {
        super(ID, info);
        setMagic(2,1);
        ExhaustiveVariable.setBaseValue(this, BASE_EXHAUSTIVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new 改动次数(0, 改动次数.Mode.UNLIMITED));
        if (player.hasPower(谱尼元素.POWER_ID) || player.hasPower(谱尼圣洁.POWER_ID)) {
            addToBot(new DrawCardAction(p, magicNumber));
        } else if (player.hasPower(混沌魔域.POWER_ID)){
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        } else {
            addToBot(new ApplyPowerAction(p, p, new 谱尼封印(p, p, magicNumber), magicNumber));
        }
    }
}