package data.ecommerce.api.service;

import data.ecommerce.api.model.Restriction;
import data.general.model.BaseResponse;

public interface RestrictionService {

    BaseResponse createRestriction (Restriction restriction);
}