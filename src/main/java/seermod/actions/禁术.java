package seermod.actions;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import seermod.cards.掉线;

import java.util.ArrayList;

public class 禁术 extends AbstractGameAction {
    private final AbstractMonster target;
    private final int threshold;
    private int totalPP = 0;
    private DamageInfo info;

    public 禁术(AbstractMonster target, int threshold, DamageInfo info) {
        this.info = info;
        this.target = target;
        this.threshold = threshold;
        this.actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> cardsToProcess = new ArrayList<>(p.hand.group);

        for (AbstractCard c : cardsToProcess) {
            int currentPP = ExhaustiveField.ExhaustiveFields.exhaustive.get(c);

            if (currentPP != -2) {
                if (currentPP > 0) {
                    totalPP += currentPP;
                }
                p.hand.moveToExhaustPile(c);
            }
        }


        if (totalPP >= threshold) {
            addToTop(new ApplyPowerAction(p, p, new BufferPower(p, 1), 1));
        } else {
            addToBot(new MakeTempCardInDiscardAction(new 掉线(), 1));
        }

        addToTop(new DamageAction(this.target, this.info, AttackEffect.FIRE));
        this.isDone = true;
    }
}


